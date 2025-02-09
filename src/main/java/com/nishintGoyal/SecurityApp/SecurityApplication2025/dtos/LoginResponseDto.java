package com.nishintGoyal.SecurityApp.SecurityApplication2025.dtos;

public class LoginResponseDto
{
    private Long id;

    private String accessToken;

    private String refreshToken;

    public LoginResponseDto()
    {

    }

    public LoginResponseDto(Long id, String accessToken, String refreshToken) {
        this.id = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
