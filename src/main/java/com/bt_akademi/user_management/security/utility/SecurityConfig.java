package com.bt_akademi.user_management.security.utility;

import com.bt_akademi.user_management.security.jwt.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // ****3 UserDetailsService implementasyonu olan sınıfı oluştulacak(CustomUserDetailsService)
    @Autowired
    private UserDetailsService userDetailsService;

    /*
        // **** 9 -> AbstaractAuthenticationService sınıfı tanımlanacak.
        kaynaklar arasi paylasim icim
        CORS (cross origin resource sharing) politikasi

        CORS konfigurasyonu ile
            izin verilen kaynaklar,
            izin verilen metotlar,
            izin verilen yollari belirleyecegiz.

        ornegin: Burasi sayesinde, uygulamay yalnizca GET istegi izni verilebilir.
                Belirli bir alan ad varsa, hostlar yazlnizca ona gore kisitlanabilir.

        CORS tarafindan bir istege izin verilmiyorsa, istek engellenir.

        Bu bir Java bean (bkz. @Bean annotion) oldugu icin,
        bundan yeni nesneler olusturulup tum aplikasyon bazinda kullanilabilir.
     */

    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer()
        {
            @Override
            public void addCorsMappings(CorsRegistry registry)
            {
                registry.addMapping("/**")
                        .allowedOrigins("*") // or: localhost
                        .allowedMethods("*"); // or: POST, GET etc.
            }
        };

    }


    // **** 8
    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // **** 7 -> JWTAuthenticationFilter ,JWTProvidable ve JWTProvider eklendi(jwt paketinin altına)
    // JWTProvidable implementasyonu olan JWTProvider içindeki generateToken() metodu,
    // token(Session ID) üretme görevini yapar.
    @Bean
    public JWTAuthenticationFilter creatJWTAuthenticationFilter() {
        return  new JWTAuthenticationFilter();
    }

    // **** 6 yetkilendirme(authorization) ile ilgili
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        // sesion kullanılmayacağından dolayı kapattık.(disable)
        httpSecurity.csrf().disable();

        /*
        SessionCreationPolicy.ALWAYS -> Freamwork, session yoksa mutlaka oluşturur.

        SessionCreationPolicy.NEVER -> Freamwork, yeni bir session hiçbir zaman oluşturmaz.

        SessionCreationPolicy.IFREQUIRED -> Freamwork , gerekliyse session oluşturur.(varsayılan hal)

        SessionCreationPolicy.STALLESS -> Freamwork, yeni bir session hiçbir zaman oluşturmaz ve kullanmaz.
         */
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        /*
         istemciler(clients), web sunucusu tarafindan desteklenen istekleri ogrenmek icin
           OPTIONS istegi yollar.(HttpMethod.OPTIONS)
           Burada, web sunucusu tarafindan desteklenen isteklere izin veriyoruz.
         */
        httpSecurity.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/api/authentication/**").permitAll()
                .anyRequest().authenticated();

        /*
        Güvenlik filtre zincirinde, UsernamePasswordAuthenticationFilter'ın öncesine
        JWTAuthenticationtionFilter() eklendi.
         */
        httpSecurity.addFilterBefore(creatJWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    // ****2 - > userdetailservice oluştulacak
    // kimlik doğrulama ile ilgili (authentication)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(createPasswordEncoder());
    }

    // ****1
    @Bean
     public PasswordEncoder createPasswordEncoder(){
        return new BCryptPasswordEncoder();
     }
}
