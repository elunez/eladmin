#!/bin/bash

# List of all DTO classes that extend BaseDTO
DTO_FILES=(
  "/Users/chanhengseang/Documents/study/backend/eladmin-tools/src/main/java/me/zhengjie/service/dto/LocalStorageDto.java"
  "/Users/chanhengseang/Documents/study/backend/eladmin-system/src/main/java/me/zhengjie/modules/maint/service/dto/DatabaseDto.java"
  "/Users/chanhengseang/Documents/study/backend/eladmin-system/src/main/java/me/zhengjie/modules/system/service/dto/DeptDto.java"
  "/Users/chanhengseang/Documents/study/backend/eladmin-system/src/main/java/me/zhengjie/modules/maint/service/dto/ServerDeployDto.java"
  "/Users/chanhengseang/Documents/study/backend/eladmin-system/src/main/java/me/zhengjie/modules/maint/service/dto/AppDto.java"
  "/Users/chanhengseang/Documents/study/backend/eladmin-system/src/main/java/me/zhengjie/modules/maint/service/dto/DeployDto.java"
  "/Users/chanhengseang/Documents/study/backend/eladmin-system/src/main/java/me/zhengjie/modules/system/service/dto/DictDto.java"
  "/Users/chanhengseang/Documents/study/backend/eladmin-system/src/main/java/me/zhengjie/modules/system/service/dto/RoleDto.java"
  "/Users/chanhengseang/Documents/study/backend/eladmin-system/src/main/java/me/zhengjie/modules/system/service/dto/MenuDto.java"
  "/Users/chanhengseang/Documents/study/backend/eladmin-system/src/main/java/me/zhengjie/modules/system/service/dto/DictDetailDto.java"
)

# Fix each file
for file in "${DTO_FILES[@]}"; do
  echo "Fixing $file"
  
  # Get the class name from the file path
  class_name=$(basename "$file" .java)
  
  # Replace "extends BaseDTO" with "extends BaseDTO<ClassNameDto>"
  sed -i '' "s/extends BaseDTO implements/extends BaseDTO<$class_name> implements/g" "$file"
done

echo "All DTO files have been updated!"
