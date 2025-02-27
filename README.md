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

# **REFLECTION 3**

---

## **Reflection on Code Coverage**

Selama proses latihan ini, saya pertama kali mendapatkan **coverage sebesar 42%** karena banyak kode yang belum ter-cover. Setelah menyadari hal ini, saya melakukan **review ulang** terhadap kode yang telah saya tulis untuk mengidentifikasi bagian yang belum diuji.

Strategi yang saya gunakan untuk memperbaikinya adalah dengan **menambahkan testing tambahan** di `ProductRepositoryTest`. Method ini secara spesifik meng-cover skenario yang sebelumnya terlewat, seperti menambahkan test edit dan find by id. Saya memastikan bahwa setiap metode dalam repository diuji, termasuk kasus sukses maupun gagal.

Kemudian, saya juga menambahkan **testing pada `ProductControllerTest`** untuk memastikan bahwa setiap endpoint telah berfungsi dengan benar. Saya memeriksa apakah response status yang dikembalikan sesuai dengan yang diharapkan,.

Selain itu, saya juga melakukan perbaikan pada **`EshopApplicationTests`** dengan menambahkan **`assertNotNull`**. Hal ini bertujuan untuk memastikan bahwa aplikasi dapat berjalan tanpa error saat diinisialisasi.

Dengan semua perbaikan ini, saya berhasil mencapai **100%** code coverage yang memastikan bahwa setiap fungsionalitas dalam aplikasi telah diuji secara menyeluruh.

## **Reflection on CI/CD**

Berdasarkan implementasi **CI/CD** yang telah saya lakukan, saya yakin bahwa pipeline saat ini telah memenuhi definisi **Continuous Integration (CI) dan Continuous Deployment (CD)**.

Pertama, setiap perubahan kode yang di-push ke repository akan secara otomatis memicu **build dan testing** memastikan bahwa kode baru tidak merusak fitur yang sudah ada. 

Kedua, saya telah menambahkan **code scanning dan analisis kualitas kode** menggunakan alat seperti **SonarCloud** yang membantu mengidentifikasi potensi masalah dalam kode sebelum proses deployment. 

Ketiga, saya telah mengimplementasikan mekanisme **auto-deploy ke PaaS**, sehingga setiap perubahan yang berhasil melewati tahap testing dan analisis akan langsung dideploy ke lingkungan production tanpa intervensi manual.

Dengan seluruh proses ini, pipeline saya telah mendukung **Continuous Integration** dengan memastikan bahwa kode diuji dan dianalisis setiap kali ada perubahan. Selain itu, **Continuous Deployment** juga tercapai karena setiap perubahan yang lolos testing dapat langsung dideploy di production environment.

# **REFLECTION 4**

---

#### Prinsip yang Diterapkan

1. **Single Responsibility Principle (SRP)**  
   Pemisahan halaman terkait seperti **Homepage**, **Car**, dan **Product** ke dalam **controller** masing-masing bertujuan agar setiap controller memiliki satu tanggung jawab utama dan tidak bercampur dengan tugas lainnya.

2. **Interface Segregation Principle (ISP)**  
   Dibuat layanan terpisah untuk **CarService** dan **ProductService** karena setiap layanan hanya memerlukan satu jenis implementasi tanpa harus bergantung pada layanan yang tidak dibutuhkan.

3. **Dependency Inversion Principle (DIP)**  
   Pada **CarController**, ketergantungan terhadap implementasi spesifik **CarServiceImpl** dihapus dan digantikan dengan **interface** sehingga pengendalian ketergantungan dilakukan melalui abstraksi.

4. **Open/Closed Principle (OCP)**  
   Metode **update()** dibuat pada model sehingga subclass tidak perlu mengubah perilaku utama model tersebut. Subclass cukup melakukan override pada metode ini untuk memperbarui data yang dibutuhkan.

#### Manfaat Menerapkan Prinsip SOLID

**Mengapa Harus Menerapkannya?**

- **Single Responsibility Principle (SRP)**: Setiap kelas hanya memiliki satu alasan untuk berubah, sehingga lebih mudah dikelola dan tidak mempengaruhi bagian lain dari sistem.
- **Open/Closed Principle (OCP)**: Memungkinkan ekstensi fitur tanpa harus memodifikasi kode yang sudah ada, menjaga stabilitas sistem.
- **Liskov Substitution Principle (LSP)**: Memastikan bahwa objek dari superclass dapat digantikan oleh objek dari subclass tanpa mengganggu jalannya program.
- **Interface Segregation Principle (ISP)**: Mencegah kelas dari keharusan mengimplementasikan metode yang tidak dibutuhkan, sehingga tetap ringan dan modular.
- **Dependency Inversion Principle (DIP)**: Mengurangi ketergantungan antara modul tinggi dan rendah dengan menggunakan abstraksi, meningkatkan fleksibilitas dan kemudahan pemeliharaan.  

#### Contoh Dampak Tanpa Prinsip SOLID

Tanpa menerapkan prinsip SOLID, kode dapat mengalami berbagai permasalahan:

1. Jika satu kelas memiliki banyak tanggung jawab (melanggar SRP), perubahan kecil bisa berdampak besar ke berbagai bagian kode lainnya.

2. Jika kode tidak mengikuti OCP, maka setiap perubahan fitur mengharuskan modifikasi kode lama dan meningkatkan risiko bug.

3.  Jika aturan LSP tidak diterapkan dengan baik, subclass mungkin tidak dapat digunakan sebagai pengganti superclass tanpa menyebabkan error.

4. Tanpa ISP, kelas akan dipaksa mengimplementasikan metode yang tidak diperlukan, membuat kode sulit dikelola.

5. Jika DIP tidak diterapkan, modul tingkat tinggi akan bergantung langsung pada modul tingkat rendah menyebabkan perubahan kecil bisa berdampak besar.

6. Struktur kode yang buruk akan membuat semakin sulit dipahami, memperlambat pengembangan, dan meningkatkan kemungkinan kesalahan.

7.  Ketergantungan yang tinggi antara berbagai komponen dapat mempersulit pengujian unit sehingga proses debugging menjadi lebih kompleks.
