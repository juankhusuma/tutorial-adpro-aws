# **REFLECTION 1**

Dalam tugas ini, beberapa prinsip *clean code* telah diterapkan:

### Meaningful Naming
Semua variabel, metode, dan kelas menggunakan nama yang deskriptif, seperti `ProductController`, `ProductService`, dan `productName` sehingga lebih mudah dipahami.

### Single Responsibility Principle (SRP)
- `ProductController` menangani request dan mengarahkan ke *view*.
- `ProductService` bertanggung jawab atas *business logic*.
- `ProductRepository` menyimpan dan mengambil data.

### Consistent Formatting
Kode memiliki format yang rapi, dengan indentasi yang sesuai dan penggunaan anotasi Spring seperti `@Controller`, `@Repository`, dan `@Autowired`.

### Avoiding Hardcoded Values
Semua data produk menggunakan model `Product`, yang membuat sistem lebih fleksibel dibandingkan menulis nilai langsung dalam kode.

Selain menjaga kualitas kode, beberapa praktik keamanan juga diterapkan:

### Avoid Direct Field Access
Akses terhadap atribut `Product` menggunakan **getter dan setter** dari Lombok (`@Getter @Setter`), menghindari akses langsung ke field.

### Preventing Direct Object Modification
Model `Product` tetap dikelola dengan baik melalui metode yang disediakan dalam *service layer* dan tidak dapat dimodifikasi langsung.

### Proper Redirection
Setelah proses *create product*, sistem menggunakan `redirect:list` untuk menghindari duplikasi data saat pengguna melakukan *refresh* pada halaman.

---

## **Reflection & Code Improvement**

Selama pengembangan, ditemukan error pada **file HTML**, yaitu:
> `Cannot resolve 'productName'`

### Penyebab:
- Template Thymeleaf tidak dapat menemukan variabel `productName` dalam objek yang diberikan.
- Ini terjadi karena dalam HTML, kita hanya menggunakan `productName`, padahal dalam model `Product`, atribut ini ada di dalam objek `product`.

### Solusi:
- Mengubah referensi di template HTML dari **`productName`** menjadi **`product.productName`** agar bisa mengakses atribut yang benar dari objek `Product`.