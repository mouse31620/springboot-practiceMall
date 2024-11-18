# Spring Boot 電商網站
本專案是一個基於 Spring Boot 的電商網站，實現了用戶管理、商品管理、訂單管理以及第三方登入功能，並支持 JWT 驗證來保護 API 接口。該網站前端使用了 Bootstrap 模板，後端整合多種框架和工具以實現高效的業務邏輯和資料庫操作。

## 功能介紹
未登入時，只能瀏覽商品
![image](https://github.com/user-attachments/assets/01d1c1db-d441-4fd6-97d8-72e16f6fd6d5)
### 1. 用戶
- 支持用戶註冊、登入功能。
  ![image](https://github.com/user-attachments/assets/9b6e5b5f-fefa-4fb9-bf23-f294008cbb5c)
  ![image](https://github.com/user-attachments/assets/a7f2f957-99c6-4357-abf3-809fb17ac926)

- 使用 JWT 實現無狀態的身份驗證，支持前端攜帶 JWT 存取受保護的 API。
- 使用Spring Security管理權限，分為 :
  - PRODUCT_MANAGE: 擁有所有產品CRUD功能
    ![image](https://github.com/user-attachments/assets/5da5d552-e313-4e76-bab0-90d47bdab1eb)
  - ORDER_MANAGE: 擁有所有訂單的CRUD功能
    ![image](https://github.com/user-attachments/assets/8154b644-1119-4f24-9c8d-115cc93f45df)

  - USER_MANAGE: 擁有所有使用者的CRUD功能
    ![image](https://github.com/user-attachments/assets/9a4793e6-a3b5-47c9-912d-a2bb5c364f54)

  - 無權限: 可瀏覽商品、註冊及登入，登入後可以新增訂單、察看及取消訂單，也可修改使用者本人相關資訊
  ![image](https://github.com/user-attachments/assets/321d80b9-f822-403f-aef0-dc0fc70140a1)

- 依照角色分配權限，設立多對多關聯保持新增彈性。
- 密碼使用 BCrypt，確保資料安全性。
### 2. 商品
- 支持分頁和排序查詢
- 商品與訂單間設立多對多關係，中繼表來記錄訂單中商品數量
### 3. 訂單
- 使用悲觀鎖在下訂單及取消訂單時保持商品數據一致性
- 庫存不足會給予使用者通知

## 技術使用
### 後端
- Spring Boot: 核心框架，用於構建 RESTful API。
- Spring Security: 用於身份驗證與授權管理。
- JWT (JSON Web Token): 無狀態的身份驗證方案。
- Spring Data JPA: 數據庫操作與 ORM 映射。
- QueryDSL: 動態查詢與條件篩選。
- H2 Database: 測試環境中的內存資料庫。
### 前端
- HTML/CSS/JavaScript: 網站前端設計與交互。
- Bootstrap: 樣式與布局框架。
### 測試工具
- 單元測試框架，用於測試業務邏輯。
- ExecutorService: 測試多線程併發。
- H2 Database: 單元測試使用的內存資料庫。
