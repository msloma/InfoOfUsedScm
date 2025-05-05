curl -X POST http://localhost:8080/api/scm \
  -H "Content-Type: application/json" \
  -d '{"assetCode":"ABC","scmType":"BITBUCKET","repositoryUrl":"https://bitbucket.org/ABC"}'