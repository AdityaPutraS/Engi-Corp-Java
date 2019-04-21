from PIL import Image
from math import floor

def merge_vertical(img_list):
    tot_width = 0
    tot_height = 0

    for image in img_list:
        width, height = image.size
        if width > tot_width:
            tot_width = width

        tot_height += height

    hasil = Image.new('RGBA', (tot_width, tot_height))

    offset = 0
    for image in img_list:
        _, height = image.size
        hasil.paste(im=image, box=(0, offset))
        offset += height

    return hasil

def merge_horizontal(img_list):
    tot_width = 0
    tot_height = 0

    for image in img_list:
        width, height = image.size
        if height > tot_height:
            tot_height = height
        tot_width += width

    hasil = Image.new('RGBA', (tot_width, tot_height))

    offset = 0
    for image in img_list:
        width, _ = image.size
        hasil.paste(im=image, box=(offset, 0))
        offset += width

    return hasil

def mirror(image):
    return image.transpose(Image.FLIP_LEFT_RIGHT)

def inter_color(persen, awal, akhir):
    pix_awal = awal.load()
    pix_akhir = akhir.load()

    for i in range(awal.size[0]):
        for j in range(awal.size[1]):
            newR = floor(pix_awal[i, j][0] + (pix_akhir[i, j][0] - pix_awal[i, j][0])*persen)
            newG = floor(pix_awal[i, j][1] + (pix_akhir[i, j][1] - pix_awal[i, j][1])*persen)
            newB = floor(pix_awal[i, j][2] + (pix_akhir[i, j][2] - pix_awal[i, j][2])*persen)
            newA = pix_awal[i, j][3]

            pix_awal[i, j] = (newR, newG, newB, newA)

def main():
    inp_path = input("Nama path : ")
    img_list = []

    while inp_path != "-1":
        image = Image.open(inp_path)
        img_list.append(image)
        inp_path = input("Nama path : ")
    
    out_file = input("Nama file output : ")

    hasil_mirror = []
    hasil_mirror.append(img_list[0])
    hasil_mirror.append(mirror(img_list[0]))
    hasil_mirror.append(mirror(img_list[1]))
    hasil_mirror.append(img_list[1])

    hasil = merge_vertical(hasil_mirror)
    first_img = hasil.copy()
    dst_img = Image.new('RGBA', hasil.size, (255, 0, 0, 255))
    
    merge_list = []
    merge_list.append(hasil)
    last_merge = hasil

    for i in range(10):
        inter_color(i/10, hasil, dst_img)
        merge_list.append(hasil)

        last_merge = merge_horizontal(merge_list)
        merge_list.clear()
        merge_list.append(last_merge)

    for i in range(10):
        inter_color(i/10, hasil, first_img)
        merge_list.append(hasil)

        last_merge = merge_horizontal(merge_list)
        merge_list.clear()
        merge_list.append(last_merge)

    last_merge.save(out_file)
main()
