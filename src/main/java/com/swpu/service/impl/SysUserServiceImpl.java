package com.swpu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swpu.common.QueryInfo;
import com.swpu.common.Result;
import com.swpu.dto.LoginDTO;
import com.swpu.dto.UserAddDTO;
import com.swpu.dto.UserUpdateDTO;
import com.swpu.entity.SysRole;
import com.swpu.entity.SysUser;
import com.swpu.entity.UserRoles;
import com.swpu.mapper.SysRoleMapper;
import com.swpu.mapper.SysUserMapper;
import com.swpu.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swpu.service.UserRolesService;
import com.swpu.utils.*;
import com.swpu.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Liyuxi
 * @since 2021-11-02
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private UserRolesService userRolesService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public Result login(LoginDTO loginDTO) {
        log.info("1.开始登陆");
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getUsername());
        log.info("判断用户是否存在，密码是否正确");
        if (null == userDetails ||
            !passwordEncoder.matches(MD5Util.md5(loginDTO.getPassword()),userDetails.getPassword())){
            return Result.fail("账号或密码错误，请重新输入");
        }
        if (!userDetails.isEnabled()){
            return Result.fail("该账号已禁用，请联系管理员");
        }
        log.info("登陆成功，在security对象中存入登陆者信息");
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.info("根据登陆信息获取token");
        //需要借助jwt生成token
        String token = tokenUtil.generateToken(userDetails);
        HashMap<String, String> map = new HashMap<>(2);
        map.put("tokenHead",tokenHead);
        map.put("token",token);
        return Result.success("登陆成功",map);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addUser(UserAddDTO dto) {
        if (dto == null){
            return Result.fail("参数不能为空");
        }
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.eq("user_name",dto.getUsername());
        qw.last("limit 1");
        SysUser one = this.getOne(qw);
        if (one != null){
            return Result.fail("已存在同名用户，请重新添加!");
        }
        SysUser sysUser = BeanCopyUtil.copyObject(dto, SysUser.class);
        sysUser.setPassword(passwordEncoder.encode(MD5Util.md5(sysUser.getPassword())));
        this.save(sysUser);
        //新增用户-角色关系
        if (dto.getRoles() != null && dto.getRoles().size() > 0){
            List<SysRole> roles = dto.getRoles();
            roles.forEach(data->{
                UserRoles userRoles = new UserRoles();
                userRoles.setUserId(sysUser.getId());
                userRoles.setRoleId(data.getId());
                userRolesService.save(userRoles);
            });
        }
        redisUtil.delKey("userInfo_" + SecurityUtil.getUsername());
        return Result.success("新增成功");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updateUser(UserUpdateDTO dto) {
        if (dto.getId() == null){
            return Result.fail("参数异常");
        }
        if (dto.getRoles() != null && dto.getRoles().size() > 0){
            //先删除旧的角色关联
            QueryWrapper<UserRoles> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id",dto.getId());
            userRolesService.remove(queryWrapper);
            //新增修改的关系关系
            dto.getRoles().forEach(data->{
                UserRoles roles = new UserRoles();
                roles.setUserId(dto.getId());
                roles.setRoleId(data.getId());
                userRolesService.save(roles);
            });
        }
        SysUser sysUser = BeanCopyUtil.copyObject(dto, SysUser.class);
        this.updateById(sysUser);
        redisUtil.delKey("userInfo_" + SecurityUtil.getUsername());
        return Result.success("修改成功");
    }

    @Override
    public Result findPage(QueryInfo queryInfo) {
        queryInfo.setPageNumber(0 == queryInfo.getPageNumber() ? 1 : queryInfo.getPageNumber());
        queryInfo.setPageSize(0 == queryInfo.getPageSize() ? 10 : queryInfo.getPageSize());
        //新建分页对象
        Page<SysUser> page = new Page<>(queryInfo.getPageNumber(),queryInfo.getPageSize());
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        if (StringUtils.hasText(queryInfo.getQueryString())){
            qw.like("username",queryInfo.getQueryString());
        }
        IPage<SysUser> iPage = super.page(page, qw);
        long total = iPage.getTotal();
        List<SysUser> records = iPage.getRecords();
        List<UserVo> userVos = BeanCopyUtil.copyList(records, UserVo.class);
        userVos.forEach(data->{
            //查询角色
            data.setRoles(sysRoleMapper.findRolesByUserId(data.getId()));
        });
        Page<UserVo> resultPage = new Page();
        resultPage.setTotal(total);
        resultPage.setRecords(userVos);
        return Result.success("分页数据",resultPage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result deleteById(Long id) {
        SysUser sysUser = this.getById(id);
        if (null == sysUser){
            return Result.fail("查询不到此用户，删除失败");
        }
        //删除用户和角色的管理关系
        List<SysRole> roleList = sysRoleMapper.getRoleByUserId(id);
        if (roleList.size() > 0){
            roleList.forEach(data->{
                QueryWrapper<UserRoles> qw = new QueryWrapper<>();
                qw.eq("user_id",id);
                qw.eq("role_id",data.getId());
                userRolesService.remove(qw);
            });
        }
        this.removeById(id);
        redisUtil.delKey("userInfo_" + SecurityUtil.getUsername());
        return Result.success("删除成功");
    }
}
