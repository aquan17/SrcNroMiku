# Ngọc Rồng Long Giới - Server

Mã nguồn Game Server Ngọc Rồng (Long Giới).

### Cấu trúc thư mục:
- `src/`: Mã nguồn Java Server
- `data/`: Tài nguyên game (icon, effect, map, config)
- `dist/`: File thực thi `Nro_Miku.jar`
- `lib/`: Thư viện phụ thuộc (`Lib_Done.jar`)
- `ngocrong.sql`: Database MySQL khởi tạo
- `run.bat`: Script khởi chạy server trên Windows

### Khởi chạy:
- Windows: Chạy file `run.bat` (yêu cầu Java 8+).
- VPS / Linux: `java -server -jar -Dfile.encoding=UTF-8 dist/Nro_Miku.jar`
