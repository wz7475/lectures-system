cls
cd frontend
call npm install
call npm run build
cd ..
rmdir /q/s .\swizleApi\src\main\resources\static
xcopy /s/e frontend\build swizleApi\src\main\resources\static\
cd swizleApi
call ./mvnw spring-boot:run