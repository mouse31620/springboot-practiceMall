package com.example.springbootpracticemall.security;

import com.example.springbootpracticemall.model.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QUser qUser = QUser.user;
        QRole qRole = QRole.role;
        QPrivilege qPrivilege = QPrivilege.privilege;
        QRolePrivileges qRolePrivileges = QRolePrivileges.rolePrivileges; // 中間表
        User user = Optional.ofNullable(jpaQueryFactory.selectFrom(qUser)
                .leftJoin(qUser.userRole, qRole)  // 連接 user 和 role
                .where(qUser.email.eq(username))
                .fetchOne())
                .orElseThrow(() -> new UsernameNotFoundException("找不到使用者"));
        List<Privilege> rolePrivileges = jpaQueryFactory.select(qPrivilege)
                .from(qRolePrivileges)
                .leftJoin(qRolePrivileges).on(qRolePrivileges.privilege.eq(qPrivilege))
                .where(qRolePrivileges.role.eq(user.getUserRole()))
                .fetch();
        Set<Privilege> privilegeSet = new HashSet<>(rolePrivileges);
        user.getUserRole().setPrivileges(privilegeSet);


        // 轉換成 Spring security指定格式
        return new CustomUserDetails(user);
    }
}
