package com.cad.book.springboot.config.auth;

import com.cad.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * @EnableWebSecurity Spring Security 설정들을 활성화시켜 준다.
 *
 * csrf().disable().headers().frameOptions().disable() : h2-console 화면을 사용하기 위해서 해당 옵션을 disable 한다.
 *
 * authorizeRequests :
 * - URL 별 권한 관리를 설정하는 옵션의 시작점,
 * - 이게 먼저 선언되어야 antMatchers 옵션을 사용할 수 있다.
 *
 * antMatchers :
 * - 권한 관리 대상을 지정하는 옵션이다.
 * - URL, HTTP 메소드별로 관리가 가능하다.
 * - "/"등 지정된 URL 들은 permitAll()옵션을 통해 전체 열람 권한을 주었다.
 * - "/api/v1/**" 주소를 가진 API 는 USER 권한을 가진 사람만 간으하도록 했다.
 *
 * anyRequest :
 * - 설정된 값들 이외 나머지 URL 들을 나타낸다.
 * - authenticated() 을 추가하여 나머지 URL 들을 모두 인증된 사용자들에게만 허용하게 한다.
 * - 인증된 사용자 즉, 로그인한 사용자들을 이야기한다.
 *
 * logout().logoutSuccessUrl("/")
 * - 로그아웃 기능에 대한 여러 설정의 진입점
 * - 로그아웃 성공 시 / 주소로 이동한다.
 *
 * oauth2Login : OAuth2 로그인 기능에 대한 여러 설정 진입점
 *
 * userInfoEndpoint : OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당한다.
 *
 * userService
 * - 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록한다.
 * - 리소스 서버(즉, 소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있다.
 *
 */
@RequiredArgsConstructor
@EnableWebSecurity
public class SpringConfig extends WebSecurityConfigurerAdapter {

  private final CustomOAuth2UserService customOAuth2UserService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
            .headers()
            .frameOptions()
            .disable()
            .and()
            .authorizeRequests()
            .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**")
            .permitAll()
            .antMatchers("/api/v1/**")
            .hasRole(Role.USER.name())
            .anyRequest()
            .authenticated()
            .and()
            .logout()
            .logoutSuccessUrl("/")
            .and()
            .oauth2Login()
            .userInfoEndpoint()
            .userService(customOAuth2UserService);

  }
}
