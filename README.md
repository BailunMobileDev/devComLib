# devComlib
【Bailun Mobile】Android - 开发使用的公共库集合


#### 添加依赖
---
- Step 1. Add the JitPack repository to your build file

  Add it in your root build.gradle at the end of repositories:
   ```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
   ```

- Step 2. Add the dependency
   ```
	dependencies {
	        implementation 'com.github.BailunMobileDev:devComLib:0.1.5'
	}
   ``` 

#### bl_commonlib
---
基类操作封装与工具类

#### bl_demo
---
公共库使用示例

#### bl_uilib
---
常见view整理

- [bl_btn](https://github.com/BailunMobileDev/devComLib/blob/master/bl_data/readme/bailunbtn.md)
