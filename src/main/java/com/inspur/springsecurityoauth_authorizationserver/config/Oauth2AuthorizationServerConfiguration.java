package com.inspur.springsecurityoauth_authorizationserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Oauth 配置
 * @Auth Malcolm
 */
@Configuration
public class Oauth2AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Value("${jwt.license}")
    private String license;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    DataSource dataSource;

    /**
     * 配置前来验证token的client需要拥有的角色,
     * 所有客户端都都拥有ROLE_TRUSTED_CLIENT角色。（数据库默认字段）
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients().checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
    }

    /**
     * //客户端配置 使用jdbc数据库存储
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(new JdbcClientDetailsService(dataSource));
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // JWT Enhancer
        List<TokenEnhancer> enhancers = new ArrayList<>();
        enhancers.add(tokenEnhancer());
        enhancers.add(jwtAccessTokenConverter());

        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(enhancers);



        // 数据库管理access_token和refresh_token
//        TokenStore tokenStore = ;

        // 用户信息查询服务
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                // 数据库管理授权信息、授权码
                .approvalStore(new JdbcApprovalStore(dataSource))
                .authorizationCodeServices(new JdbcAuthorizationCodeServices(dataSource))
                // token 相关
//                .tokenStore(new JdbcTokenStore(dataSource))
                .tokenEnhancer(enhancerChain)
                .reuseRefreshTokens(true)
                .tokenStore(jwtTokenStore())
                .accessTokenConverter(jwtAccessTokenConverter())
                // 允许 GET、POST 请求获取 token，即访问端点：oauth/token;
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);

//        DefaultTokenServices tokenServices = new DefaultTokenServices();
//        tokenServices.setTokenEnhancer(enhancerChain);
//        tokenServices.setTokenStore(jwtTokenStore());
//        tokenServices.setTokenStore(new JdbcTokenStore(dataSource));
//        tokenServices.setSupportRefreshToken(true);
//        tokenServices.setAccessTokenValiditySeconds(180);
//        tokenServices.setRefreshTokenValiditySeconds(180);
//        endpoints.tokenServices(tokenServices);
    }

    /**
     * JWT相关 store converter enhancer
     * @return
     */
    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey(license);
        return accessTokenConverter;
    }
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new JWTTokenEnhancer();
    }

}
