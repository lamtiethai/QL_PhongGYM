package com.pvh.gym_management.configurations;

import com.pvh.gym_management.services.Impl.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    @Lazy
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    private List<String> publicUrl = List.of("/",
//            "api/**"
//    );


//    private List<String> companyManagerUrl = List.of("/",
//            "/api/cars/**",
//            "/api/companies/manager/company/**",
//            "/api/companies/manager/**",
//            "/api/companies/cargo/**",
//            "/api/drivers/company/**",
//            "/api/drivers/verify/**",
//            "/api/drivers/available",
//            "/api/routes/add",
//            "/api/routes/company/**",
//            "/api/statistics/annual/**",
//            "/api/statistics/quarterly/**",
//            "/api/statistics/daily/**",
//            "/api/trips/create"
//
//    );
//
//    private List<String> driverUrl = List.of("/",
//            "/api/trips/driver/**"
//    );
//
//
//    private List<String> adminUrl = List.of("/",
//            "/api/admin/**",
//            "/api/companies/verify/**",
//            "/api/statistics/user-statistics",
//            "/api/statistics/annual-revenue",
//            "/api/statistics/bar-data"
//    );



//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(publicUrl.toArray(new String[0])).permitAll()
//
////                        .requestMatchers(companyManagerUrl.toArray(new String[0])).hasRole("COMPANY_MANAGER")
////
////                        .requestMatchers(driverUrl.toArray(new String[0])).hasRole("DRIVER")
////
////                        .requestMatchers(adminUrl.toArray(new String[0])).hasRole("ADMIN")
//
//                        .anyRequest().authenticated()
//                )
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout")
//                        .permitAll());
//
//        return http.build();
//    }
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    .anyRequest().permitAll()  // Cho phép truy cập tất cả các yêu cầu mà không cần xác thực
            )
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .permitAll());

    return http.build();
}


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}