# lawbot-web

## 服务端
* lawbot-sys 提供用户相关接口，使用spring session实现单点登录
* lawbot-case-reco 提供同案推荐接口
* lawbot-case-award 提供裁决书接口
* lawbot-case-contract 提供合同接口
* lawbot-chatbot chatbot的访问控制

SETUP
```
# package all service
mvn clean package

```
## 前端
* lawbot Portal页面
* lawbot-case-reco 同案推荐操作页面
* lawbot-case-award 裁决书操作页面
* lawbot-case-contract 合同操作页面

SETUP
```
# install the dependencies
npm install

# run developer env
npm run start

# build the production 
npm run build
```

## 部署
使用nginx反向代理
```
/account/ proxy to lawbot-sys
/reco/api/ proxy to lawbot-case-reco
/award/api proxy to lawbot-case-award
/contract/api proxy to lawbot-case-contract

```
