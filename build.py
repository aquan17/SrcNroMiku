import os
import subprocess
import shutil
import sys
import zipfile

base = os.path.dirname(os.path.abspath(__file__))
os.chdir(base)

print("==============================================")
print("       BUILD NRO YUKI GAME SERVER")
print("==============================================")

build_dir = "build_classes"
if os.path.exists(build_dir):
    shutil.rmtree(build_dir)
os.makedirs(build_dir, exist_ok=True)
os.makedirs("dist", exist_ok=True)

# Tim toan bo file .java
java_files = []
for root, _, files in os.walk("src"):
    for f in files:
        if f.endswith(".java"):
            java_files.append(os.path.join(root, f).replace("\\", "/"))

sources_txt = "sources.txt"
with open(sources_txt, "w", encoding="utf-8") as fh:
    for jf in java_files:
        fh.write(f'"{jf}"\n')

print(f"[1/3] Da tim thay {len(java_files)} file Java. Dang bien dich...")
javac_cmd = [
    "javac",
    "--release", "17",
    "-encoding", "UTF-8",
    "-d", build_dir,
    "-cp", "lib/Lib_Done.jar",
    "@sources.txt"
]

res = subprocess.run(javac_cmd, capture_output=True, text=True, encoding="utf-8", errors="ignore")

if os.path.exists(sources_txt):
    os.remove(sources_txt)

if res.returncode != 0:
    print("\n[LOI BIEN DICH]:")
    print(res.stderr[:3000])
    sys.exit(1)

print("[2/3] Bien dich thanh cong! Dang dong goi file dist/Nro_Miku.jar...")
jar_file = "dist/Nro_Miku.jar"
manifest = "manifest.mf"

with zipfile.ZipFile(jar_file, "w", zipfile.ZIP_DEFLATED) as zf:
    zf.write(manifest, "META-INF/MANIFEST.MF")
    for root, _, files in os.walk(build_dir):
        for f in files:
            full_path = os.path.join(root, f)
            rel_path = os.path.relpath(full_path, build_dir).replace("\\", "/")
            zf.write(full_path, rel_path)

if os.path.exists(build_dir):
    shutil.rmtree(build_dir)

sz = os.path.getsize(jar_file) / (1024 * 1024)
print(f"[3/3] Dong goi hoan tat: dist/Nro_Miku.jar ({sz:.2f} MB)")
print("==============================================")
print("  [THANH CONG] Server NRO Yuki da san sang!")
print("==============================================")
