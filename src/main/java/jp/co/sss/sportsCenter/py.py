import glob

paths = glob.glob(r"C:/Users/losin/Pictures/Screenshots/*.png")
for path in paths:
    print(path)