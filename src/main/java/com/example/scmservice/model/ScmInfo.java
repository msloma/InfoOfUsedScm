package com.example.scmservice.model;

import java.io.Serializable;
import java.util.Objects;

public class ScmInfo implements Serializable {
    private String assetCode;
    private String scmType;
    private String repositoryUrl;

    // Default constructor for serialization
    public ScmInfo() {
    }

    public ScmInfo(String assetCode, String scmType, String repositoryUrl) {
        this.assetCode = assetCode;
        this.scmType = scmType;
        this.repositoryUrl = repositoryUrl;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getScmType() {
        return scmType;
    }

    public void setScmType(String scmType) {
        this.scmType = scmType;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScmInfo scmInfo = (ScmInfo) o;
        return Objects.equals(assetCode, scmInfo.assetCode) &&
                Objects.equals(scmType, scmInfo.scmType) &&
                Objects.equals(repositoryUrl, scmInfo.repositoryUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assetCode, scmType, repositoryUrl);
    }

    @Override
    public String toString() {
        return "ScmInfo{" +
                "assetCode='" + assetCode + '\'' +
                ", scmType='" + scmType + '\'' +
                ", repositoryUrl='" + repositoryUrl + '\'' +
                '}';
    }
}
