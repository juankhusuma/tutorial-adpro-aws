# **REFLECTION 1**

Dalam tugas ini, beberapa prinsip *clean code* telah diterapkan:

### Meaningful Naming
Semua variabel, metode, dan class menggunakan nama yang deskriptif, seperti `ProductController`, `ProductService`, dan `productName` sehingga lebih mudah dipahami.

### Single Responsibility Principle (SRP)
- `ProductController` menangani request dan mengarahkan ke *view*.
- `ProductService` bertanggung jawab atas *business logic*.
- `ProductRepository` menyimpan dan mengambil data.

### Avoiding Hardcoded Values
Semua data produk menggunakan model `Product` yang membuat sistem lebih fleksibel dibandingkan menulis nilai langsung dalam kode.

---

## **Reflection & Code Improvement**

Saya menyadari bahwa dalam implementasi awal, saya belum menambahkan validasi input (error handling) untuk memastikan data yang dimasukkan pengguna valid sebelum disimpan ke database. 


Saya juga tidak menambahkan komen pada kode karena kode dapat dipahami.

Kemudian, untuk kode create saya menambahkan UUID set product agar pada saat product dibuat, `stringId` akan memiliki value. Saya mendapati error jika tidak menambahkan UUID karena product tidak menyimpan ID.

```
    @GetMapping("/create")
    public String createProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return  "createProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model) {
        product.setProductId(UUID.randomUUID().toString());
        service.create(product);
        return "redirect:list";
    }
```


# **REFLECTION 2**

---

## **Reflection on Unit Testing**

Setelah membuat unit test, saya merasa banyak pertimbangan yang harus dilakukan. Salah satunya adalah menentukan skenario apa saja yang harus diuji. Menurut saya, jumlah unit test dalam satu kelas tergantung pada kompleksitas logika bisnis yang ada di dalamnya. Setiap metode penting harus diuji dengan berbagai kemungkinan input, termasuk kasus normal, batas, dan kesalahan.

Untuk memastikan bahwa unit test yang dibuat sudah cukup, kita bisa menggunakan code coverage sebagai metrik. Code coverage membantu memahami seberapa banyak kode yang telah diuji oleh unit test. Namun, memiliki 100% code coverage tidak selalu berarti bahwa kode kita bebas dari bug atau error karena ada kemungkinan adanya logical errors yang tidak tertangkap oleh unit test. Oleh karena itu, selain coverage, penting juga untuk memastikan bahwa test case mencakup semua skenario yang mungkin terjadi dalam aplikasi.

---

## **Reflection on Functional Testing**

Ketika diminta untuk membuat fungsional test baru yang memverifikasi jumlah item dalam daftar produk, saya sadar bahwa menyalin kode dari *test suite* sebelumnya dapat menyebabkan kode yang tidak bersih. Jika setiap *test suite* memiliki prosedur setup dan variabel instance yang sama, ini bisa menyebabkan **code duplication**, yang bertentangan dengan prinsip DRY (*Don't Repeat Yourself*).

### **Hal yang salah menurut aturan clean code:**
1. **Code Duplication**: Menyalin prosedur setup dan variabel instance ke beberapa *test suite* meningkatkan kemungkinan inkonsistensi dan kesulitan dalam pemeliharaan.
2. **Kurangnya Reusability**: Jika ada perubahan dalam proses setup, kita harus memperbarui banyak kelas secara manual.
3. **Dibacanya buruk**: Dengan banyaknya duplikasi, kode menjadi lebih sulit dibaca dan dipahami.

### **Code improvement:**
- **Menggunakan Base Test Class**: Buat base class untuk *functional test* yang berisi metode setup yang dapat digunakan kembali oleh semua *test suite*.
- **Refactor dengan Utility Methods**: Jika ada prosedur umum yang sering digunakan, buat metode *utility* agar tidak perlu menyalin kode yang sama ke berbagai kelas.
- **Gunakan Parameterized Tests**: Jika ada banyak skenario yang mirip, gunakan parameterized tests untuk menghindari kode yang berulang.