-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 25 Jul 2024 pada 13.40
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_apotik_pharmacy_uas`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_entry_transaksi`
--

CREATE TABLE `tb_entry_transaksi` (
  `id_entry_transaksi` int(11) NOT NULL,
  `kode_transaksi` varchar(255) NOT NULL,
  `kode_obat` varchar(255) NOT NULL,
  `nama_obat` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `harga_item` int(11) NOT NULL,
  `total` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tb_entry_transaksi`
--

INSERT INTO `tb_entry_transaksi` (`id_entry_transaksi`, `kode_transaksi`, `kode_obat`, `nama_obat`, `quantity`, `harga_item`, `total`) VALUES
(3, 'T0001', 'I0001', 'Amoxicillin', 1, 2000, 2000),
(4, 'T0001', 'I0006', 'Attapulgite', 1, 1500, 1500),
(5, 'T0002', 'I0007', 'Betadine', 1, 10000, 10000),
(6, 'T0002', 'I0008', 'Rivanol', 1, 5000, 5000),
(7, 'T0003', 'I0010', 'OBH Combi', 2, 10000, 20000),
(9, 'T0003', 'I0001', 'Amoxicillin', 1, 2000, 2000),
(10, 'T0004', 'I0001', 'Amoxicillin', 1, 2000, 2000),
(24, 'T0005', 'I0001', 'Amoxicillin', 2, 2000, 4000),
(29, 'T0006', 'I0001', 'Amoxicillin', 1, 2000, 2000),
(30, 'T0006', 'I0008', 'Rivanol', 1, 5000, 5000),
(31, 'T0006', 'I0009', 'Woods', 1, 12000, 12000);

--
-- Trigger `tb_entry_transaksi`
--
DELIMITER $$
CREATE TRIGGER `update_stok_obat_on_delete` AFTER DELETE ON `tb_entry_transaksi` FOR EACH ROW BEGIN
    UPDATE tb_obat AS o
    SET o.stok_obat = o.stok_obat + OLD.quantity
    WHERE o.kode_obat = OLD.kode_obat;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `update_stok_obat_on_insert` AFTER INSERT ON `tb_entry_transaksi` FOR EACH ROW BEGIN
UPDATE tb_obat as o
  SET o.stok_obat = o.stok_obat - NEW.quantity
WHERE o.kode_obat = NEW.kode_obat;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `update_stok_obat_on_update` AFTER UPDATE ON `tb_entry_transaksi` FOR EACH ROW BEGIN 
    DECLARE qty_change INT;
    SET qty_change = NEW.quantity - OLD.quantity;
    
    IF qty_change > 0 THEN
        UPDATE tb_obat AS o
        SET o.stok_obat = o.stok_obat - qty_change
        WHERE o.kode_obat = NEW.kode_obat;
    ELSEIF qty_change < 0 THEN
        UPDATE tb_obat AS o
        SET o.stok_obat = o.stok_obat + ABS(qty_change)
        WHERE o.kode_obat = NEW.kode_obat;
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_obat`
--

CREATE TABLE `tb_obat` (
  `kode_obat` varchar(255) NOT NULL,
  `nama_obat` varchar(255) NOT NULL,
  `harga_beli` double NOT NULL,
  `harga_jual` double NOT NULL,
  `kategori` enum('Antibiotics','Antivirus','Antidiare','Antiseptik','Obat_Batuk') NOT NULL,
  `stok_obat` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tb_obat`
--

INSERT INTO `tb_obat` (`kode_obat`, `nama_obat`, `harga_beli`, `harga_jual`, `kategori`, `stok_obat`) VALUES
('I0001', 'Amoxicillin', 1900, 2000, 'Antibiotics', 98),
('I0002', 'Cefadroxil', 2000, 2500, 'Antibiotics', 100),
('I0003', 'Ciprofloxacin', 2800, 3000, 'Antibiotics', 100),
('I0004', 'Ribavirin', 10000, 12000, 'Antivirus', 100),
('I0005', 'Oralit', 800, 1000, 'Antidiare', 100),
('I0006', 'Attapulgite', 1000, 1500, 'Antidiare', 100),
('I0007', 'Betadine', 9500, 10000, 'Antiseptik', 100),
('I0008', 'Rivanol', 4700, 5000, 'Antiseptik', 99),
('I0009', 'Woods', 10000, 12000, 'Obat_Batuk', 99),
('I0010', 'OBH Combi', 8000, 10000, 'Obat_Batuk', 100),
('I0011', 'Vicks Formula 44', 92000, 10000, 'Obat_Batuk', 100);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_pegawai`
--

CREATE TABLE `tb_pegawai` (
  `kode_pegawai` varchar(255) NOT NULL,
  `nama_pegawai` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `tgl_masuk` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tb_pegawai`
--

INSERT INTO `tb_pegawai` (`kode_pegawai`, `nama_pegawai`, `username`, `password`, `tgl_masuk`) VALUES
('P0001', 'admin', 'admin', 'DGYYLscQhABl66pHxebOkA==', '2024-07-25'),
('P0002', 'Asep Supriyadi', 'asep', 'DGYYLscQhABl66pHxebOkA==', '2024-07-25'),
('P0003', 'Agnia Gianti Vani', 'ayang', 'DGYYLscQhABl66pHxebOkA==', '2024-07-25'),
('P0005', 'rahman', 'rahman', 'DGYYLscQhABl66pHxebOkA==', '2024-07-25');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_pelanggan`
--

CREATE TABLE `tb_pelanggan` (
  `id_pelanggan` int(11) NOT NULL,
  `nama_pelanggan` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `no_telp` varchar(255) NOT NULL,
  `alamat` varchar(255) NOT NULL,
  `tgl_dibuat` date DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tb_pelanggan`
--

INSERT INTO `tb_pelanggan` (`id_pelanggan`, `nama_pelanggan`, `email`, `no_telp`, `alamat`, `tgl_dibuat`) VALUES
(1, 'Alice Johnson', 'alice.johnson@example.com', '+62 812-3456-7890', 'Jl. Merpati No. 15, Jakarta, Indonesia', '2024-07-25'),
(2, 'Bob Smith', 'bob.smith@example.com', '+62 812-9876-5432', 'Jl. Pahlawan No. 22, Bandung, Indonesia', '2024-07-25'),
(3, 'Carol Davis', 'carol.davis@example.com', '+62 811-2345-6789', 'Jl. Sukajadi No. 8, Surabaya, Indonesia', '2024-07-25'),
(4, 'David Brown', 'david.brown@example.com', '+62 813-4567-8901', 'Jl. Kebon Jeruk No. 5, Medan, Indonesia', '2024-07-25'),
(5, 'Eva Green', 'eva.green@example.com', '+62 812-6789-0123', 'Jl. Senen No. 3, Yogyakarta, Indonesia', '2024-07-25'),
(6, 'Frank White', 'frank.white@example.com', '+62 811-3456-7890', 'Jl. Pasar Baru No. 12, Semarang, Indonesia', '2024-07-25'),
(7, 'Grace Wilson', 'grace.wilson@example.com', '+62 813-5678-9012', 'Jl. Jendral Sudirman No. 18, Palembang, Indonesia', '2024-07-25'),
(8, 'Henry Lee', 'henry.lee@example.com', '+62 812-7890-1234', 'Jl. S Parman No. 20, Makassar, Indonesia', '2024-07-25'),
(9, 'Irene Taylor', 'irene.taylor@example.com', '+62 811-4567-8901', 'Jl. Soekarno Hatta No. 25, Batam, Indonesia', '2024-07-25'),
(10, 'Jack Harris', 'jack.harris@example.com', '+62 813-6789-0123', 'Jl. Raya No. 30, Balikpapan, Indonesia', '2024-07-25');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_transaksi`
--

CREATE TABLE `tb_transaksi` (
  `kode_transaksi` varchar(255) NOT NULL,
  `id_pelanggan` int(11) NOT NULL,
  `total_pembelian` double NOT NULL,
  `total_dibayarkan` double NOT NULL,
  `total_kembalian` double NOT NULL,
  `tgl_transaksi` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tb_transaksi`
--

INSERT INTO `tb_transaksi` (`kode_transaksi`, `id_pelanggan`, `total_pembelian`, `total_dibayarkan`, `total_kembalian`, `tgl_transaksi`) VALUES
('T0001', 1, 3500, 5000, 1500, '2024-07-25'),
('T0002', 1, 15000, 20000, 5000, '2024-07-25'),
('T0003', 1, 22000, 50000, 28000, '2024-07-25'),
('T0004', 9, 2000, 5000, 3000, '2024-07-25'),
('T0005', 1, 4000, 5000, 1000, '2024-07-25'),
('T0006', 1, 19000, 20000, 1000, '2024-07-25');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tb_entry_transaksi`
--
ALTER TABLE `tb_entry_transaksi`
  ADD PRIMARY KEY (`id_entry_transaksi`);

--
-- Indeks untuk tabel `tb_obat`
--
ALTER TABLE `tb_obat`
  ADD PRIMARY KEY (`kode_obat`);

--
-- Indeks untuk tabel `tb_pegawai`
--
ALTER TABLE `tb_pegawai`
  ADD PRIMARY KEY (`kode_pegawai`);

--
-- Indeks untuk tabel `tb_pelanggan`
--
ALTER TABLE `tb_pelanggan`
  ADD PRIMARY KEY (`id_pelanggan`);

--
-- Indeks untuk tabel `tb_transaksi`
--
ALTER TABLE `tb_transaksi`
  ADD PRIMARY KEY (`kode_transaksi`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `tb_entry_transaksi`
--
ALTER TABLE `tb_entry_transaksi`
  MODIFY `id_entry_transaksi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT untuk tabel `tb_pelanggan`
--
ALTER TABLE `tb_pelanggan`
  MODIFY `id_pelanggan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
