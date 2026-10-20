# NRO Yuki - Game Server

Mã nguồn Game Server Ngọc Rồng Yuki (NRO Yuki).

### Cấu trúc thư mục:
- `src/`: Mã nguồn Java Server
- `data/`: Tài nguyên game (icon, effect, map, config)
- `dist/`: File thực thi `Nro_Miku.jar`
- `lib/`: Thư viện phụ thuộc (`Lib_Done.jar`)
- `ngocrong.sql`: Database MySQL khởi tạo
- `build.bat` / `build.py`: Biên dịch mã nguồn Java 1-click không cần NetBeans
- `run.bat`: Script khởi chạy server trên Windows
- `run.sh`: Script khởi chạy server trên Linux / VPS

### Khởi chạy:
- Windows: Chạy file `run.bat`
- Linux / VPS: Chạy `./run.sh`
- Biên dịch lại code: Click `build.bat` (hoặc `python build.py`)
