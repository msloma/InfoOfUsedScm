package com.example.scmservice.model;

public class ScmInfo {
    private String assetCode;
    private String scmType;
    private String repositoryUrl;
    
    // Constructors
    public ScmInfo() {}
    
    public ScmInfo(String assetCode, String scmType, String repositoryUrl) {
        this.assetCode = assetCode;
        this.scmType = scmType;
        this.repositoryUrl = repositoryUrl;
    }
    
    // Getters and setters
    public String getAssetCode() { return assetCode; }
    public void setAssetCode(String assetCode) { this.assetCode = assetCode; }
    
    public String getScmType() { return scmType; }
    public void setScmType(String scmType) { this.scmType = scmType; }
    
    public String getRepositoryUrl() { return repositoryUrl; }
    public void setRepositoryUrl(String repositoryUrl) { this.repositoryUrl = repositoryUrl; }
}