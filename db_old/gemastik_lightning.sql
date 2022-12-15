-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 14, 2022 at 05:40 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gemastik_lightning`
--

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE IF NOT EXISTS `barang` (
  `id_barang` varchar(6) NOT NULL,
  `nama_barang` varchar(30) NOT NULL,
  `jenis_barang` enum('SNACK','MAKANAN','MINUMAN','ATK') DEFAULT NULL,
  `stok` int(5) DEFAULT NULL,
  `harga_beli` int(11) NOT NULL,
  `harga_jual` int(11) NOT NULL,
  PRIMARY KEY (`id_barang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`id_barang`, `nama_barang`, `jenis_barang`, `stok`, `harga_beli`, `harga_jual`) VALUES
('BG001', 'aqua cleo', 'MINUMAN', 70, 2000, 2500),
('BG003', 'Teh Pucuk', 'MINUMAN', 10, 2000, 4000),
('BG006', 'Indomilk', 'MINUMAN', 15, 2000, 2500),
('BG007', 'Kertas Folio', 'ATK', 322, 100, 250),
('BG008', 'Kertas Hvs', 'ATK', 619, 100, 250),
('BG009', 'Pulpen Snowman', 'ATK', 93, 1000, 2000),
('BG010', 'Spidol Hitam', 'ATK', 25, 2000, 2500),
('BG011', 'Spidol Merah', 'ATK', 53, 2000, 2500),
('BG012', 'Spidol Biru', 'ATK', 57, 2000, 2500),
('BG013', 'Yupi Candy', 'SNACK', 50, 400, 1000),
('BG014', 'Roti', 'SNACK', 74, 1500, 2000),
('BG015', 'Wafer', 'SNACK', 32, 500, 1000),
('BG016', 'Oreo Chocolate Cream', 'SNACK', 40, 1000, 2000),
('BG017', 'Indomie Goreng', 'MAKANAN', 110, 2500, 3500),
('BG018', 'Fitbar Tiramisu Snack', 'SNACK', 53, 3000, 4000),
('BG019', 'Lays  ', 'SNACK', 11, 4000, 5000),
('BG020', 'Slai Olai', 'SNACK', 36, 6000, 8000),
('BG021', 'Cadbury Chocolate', 'SNACK', 39, 8000, 10000),
('BG022', 'Beng Beng', 'SNACK', 43, 3000, 4000),
('BG023', 'Nabati Richocho Wafer', 'SNACK', 64, 2000, 3000),
('BG024', 'Choki Choki Cashnew', 'SNACK', 30, 3000, 5000),
('BG025', 'Sari Gandum', 'SNACK', 67, 4000, 5500),
('BG026', 'Nissin Crackers', 'SNACK', 68, 4000, 6000),
('BG027', 'Nabati Richeese Wafer', 'SNACK', 35, 2000, 3500),
('BG028', 'Tango Drink Berry', 'SNACK', 43, 4000, 5000),
('BG029', 'Hexos Candy', 'SNACK', 65, 5000, 6500),
('BG030', 'Pop Mie Mi Instan', 'MAKANAN', 11, 4560, 5200),
('BG031', 'Sedaap Mie Instan', 'MAKANAN', 30, 2500, 3500),
('BG032', 'Meiji Biscuit Hello Panda', 'SNACK', 23, 3000, 4500),
('BG033', 'Good Time Cookies', 'SNACK', 38, 5000, 7500),
('BG034', 'Aice Ice Cream Cookies', 'MINUMAN', 23, 5000, 6000),
('BG035', 'Madu Tj', 'MINUMAN', 35, 6000, 8000),
('BG036', 'Sedaap Mie Instant Cup', 'MAKANAN', 12, 2700, 3600),
('BG037', 'Energen Cereal', 'MINUMAN', 11, 4000, 5500),
('BG038', 'Gery Crackers Malkist', 'SNACK', 24, 2600, 3500),
('BG039', 'Kraft Sandwich Mini Oreo', 'SNACK', 21, 4000, 5000),
('BG040', 'Hollanda Gery Butter Cokkies', 'SNACK', 31, 21700, 25000),
('BG041', 'Minute Maid Juice', 'MINUMAN', 23, 4000, 5000),
('BG042', 'Mister Potato Snack', 'SNACK', 36, 4500, 6000),
('BG043', 'Kin A2 Yogurt', 'MINUMAN', 11, 4000, 5000),
('BG044', 'Qtela', 'SNACK', 8, 12000, 15000),
('BG045', 'Chitato', 'SNACK', 4, 24000, 27000),
('BG046', 'Piattos', 'SNACK', 58, 7000, 9500),
('BG047', 'Potabee', 'SNACK', 30, 8800, 10000),
('BG048', 'Kusuka', 'SNACK', 40, 13250, 14500),
('BG049', 'Tic Tac', 'SNACK', 33, 4250, 6500),
('BG050', 'Frech Fries', 'SNACK', 7, 6100, 7500),
('BG051', 'Dairy Milk', 'SNACK', 20, 7000, 9000),
('BG052', 'Silver Queen', 'SNACK', 71, 8500, 10500),
('BG054', 'Kacang Garuda Atom Pedas', 'SNACK', 36, 18800, 21000),
('BG055', 'Mister Potato Snack Crips', 'SNACK', 43, 10900, 12500),
('BG056', 'Roma Biscuit Sandwitch', 'SNACK', 52, 8000, 10000),
('BG057', 'Nextar Nastar Cookies', 'SNACK', 34, 1300, 2600),
('BG058', 'Mentos Candy', 'SNACK', 25, 3000, 4500),
('BG059', 'You C Healt Drink', 'MINUMAN', 19, 6000, 8200),
('BG060', 'Buavita Juice Jambu', 'MINUMAN', 21, 8900, 11500),
('BG061', 'Buavita Juice Orange', 'MINUMAN', 14, 8900, 11500),
('BG062', 'Buavita Juice Manggo', 'MINUMAN', 17, 8900, 11500),
('BG063', 'Minute Maid Juice Pulpy', 'MINUMAN', 12, 4000, 5000),
('BG064', 'Indomie Goreng Rasa Sate', 'MAKANAN', 24, 2900, 4000),
('BG065', 'Indomie Goreng Rasa Rendang', 'MAKANAN', 43, 3000, 4100),
('BG066', 'Indomie Goreng Rasa Pedas', 'MAKANAN', 13, 2750, 3500),
('BG067', 'Indomie Rasa Soto Spesial', 'MAKANAN', 31, 2850, 3600),
('BG068', 'Indomie Rasa Ayam Bawang', 'MAKANAN', 42, 2900, 4000),
('BG069', 'Indomie Kuliner Soto Padang', 'MAKANAN', 26, 3000, 4500),
('BG070', 'Indomie Kuliner Cakalang', 'MAKANAN', 43, 3000, 4500),
('BG071', 'Indomie Kuliner Ayam Medan', 'MAKANAN', 5, 3000, 4000),
('BG072', 'Sariroti ', 'MAKANAN', 10, 4000, 5000),
('BG073', 'Chitato', 'MAKANAN', 20, 4000, 5000),
('BG074', 'Roti Tawar', 'MAKANAN', 10, 7000, 8000),
('BG075', 'Piatoz', 'SNACK', 10, 2000, 3000);

-- --------------------------------------------------------

--
-- Table structure for table `karyawan`
--

CREATE TABLE IF NOT EXISTS `karyawan` (
  `id_karyawan` varchar(5) NOT NULL,
  `nama_karyawan` varchar(50) NOT NULL,
  `no_telp` varchar(15) NOT NULL,
  `alamat` text NOT NULL,
  KEY `id_karyawan` (`id_karyawan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `karyawan`
--

INSERT INTO `karyawan` (`id_karyawan`, `nama_karyawan`, `no_telp`, `alamat`) VALUES
('PG001', 'Developer', '085655864624', 'Nganjuk, Jawa Timur, Indonesia'),
('PG002', 'Achmad Baihaqi', '085655864624', 'Jombang, Jawa Timur, Indonesia'),
('PG003', 'Amirzan Fikri Prasetyo', '085105304100', 'Jombang, Jawa Timur, Indonesia'),
('PG004', 'Mohammad Ilham Islamy', '085784626830', 'Nganjuk, Jawa Timur, Indonesia');

-- --------------------------------------------------------

--
-- Table structure for table `pembeli`
--

CREATE TABLE IF NOT EXISTS `pembeli` (
  `id_pembeli` varchar(5) NOT NULL,
  `nama_pembeli` varchar(50) NOT NULL,
  `no_telp` varchar(15) DEFAULT NULL,
  `alamat` text NOT NULL,
  KEY `id_pembeli` (`id_pembeli`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pembeli`
--

INSERT INTO `pembeli` (`id_pembeli`, `nama_pembeli`, `no_telp`, `alamat`) VALUES
('PB001', 'Abdul Fattah Burhanuddin', '087825323132', 'Nganjuk, Indonesia'),
('PB002', 'Abdulloh Kafabi', '080981734812', 'Nganjuk, Indonesia'),
('PB003', 'Achmad Romadoni', '083849503086', 'Kediri, Indonesia'),
('PB004', 'Afif Fitra Nugroho', '082223490056', 'Nganjuk, Indonesia'),
('PB005', 'Aga Deva Kharisma', '089756124356', 'Nganjuk, Indonesia'),
('PB006', 'Ahmad Edy Syaputra', '082131788391', 'Kediri, Indonesia'),
('PB007', 'Ahmad Fauzi Waro', '085893072338', 'Kediri, Indonesia'),
('PB008', 'Ahmad Riyanto', '085735973607', 'Jombang, Indonesia'),
('PB009', 'Andi Wijahnu Prasetyo', '085708298074', 'Nganjuk, Indonesia'),
('PB010', 'Andre Maulana', '082231253269', 'Nganjuk, Indonesia');

-- --------------------------------------------------------

--
-- Table structure for table `saldo`
--

CREATE TABLE IF NOT EXISTS `saldo` (
  `id_saldo` varchar(10) NOT NULL,
  `jumlah_saldo` bigint(20) NOT NULL,
  PRIMARY KEY (`id_saldo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `saldo`
--

INSERT INTO `saldo` (`id_saldo`, `jumlah_saldo`) VALUES
('S001', 1025750);

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE IF NOT EXISTS `supplier` (
  `id_supplier` varchar(5) NOT NULL,
  `nama_supplier` varchar(50) NOT NULL,
  `no_telp` varchar(15) NOT NULL,
  `alamat` text NOT NULL,
  KEY `id_supplier` (`id_supplier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`id_supplier`, `nama_supplier`, `no_telp`, `alamat`) VALUES
('SP001', 'Moch. Alvian Hidayatulloh', '085105304100', 'Jombang, Jawa Timur, Indonesia'),
('SP002', 'Syafrizal Wd Mahendra', '0895807400305', 'Nganjuk, Jawa Timur, Indonesia'),
('SP003', 'Syamaidzar Adani Syah', '082229657300', 'Nganjuk, Jawa Timur, Indonesia'),
('SP004', 'Moch. Rifaul Ardiyanto', '085855348099', 'Nganjuk, Jawa Timur, Indonesia'),
('SP005', 'Septian Yoga Pamungkas', '085806531609', 'Nganjuk, Jawa Timur, Indonesia'),
('SP006', 'Atilah Lazuardi Azra', '081335315751', 'Nganjuk, Jawa Timur, Indonesia'),
('SP007', 'Farid Kurniawan', '083851952383', 'Nganjuk, Jawa Timur, Indonesia'),
('SP008', 'Muhamad Sugeng Cahyono', '085812455631', 'Kediri, Jawa Timur, Indonesia'),
('SP009', 'Wisudawan Siburian', '081361977793', 'Nganjuk, Jawa Timur, Indonesia'),
('SP010', 'Wahyu Bagas Prastyo', '081913236990', 'Nganju, Jawa Timur, Indonesia');

-- --------------------------------------------------------

--
-- Table structure for table `transaksi_beli`
--

CREATE TABLE IF NOT EXISTS `transaksi_beli` (
  `id_tr_beli` varchar(7) NOT NULL,
  `nama_tr_beli` text DEFAULT NULL,
  `id_karyawan` varchar(5) NOT NULL,
  `id_supplier` varchar(5) NOT NULL,
  `id_barang` varchar(6) NOT NULL,
  `jumlah_brg` int(5) NOT NULL,
  `total_hrg` int(11) NOT NULL,
  `tanggal` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_tr_beli`),
  KEY `id_supplier` (`id_supplier`),
  KEY `id_barang` (`id_barang`),
  KEY `id_petugas` (`id_karyawan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaksi_beli`
--

INSERT INTO `transaksi_beli` (`id_tr_beli`, `nama_tr_beli`, `id_karyawan`, `id_supplier`, `id_barang`, `jumlah_brg`, `total_hrg`, `tanggal`) VALUES
('TRB0001', 'membeli galon', 'PG003', 'SP001', 'BG001', 1, 2000, '2022-12-08 21:33:33'),
('TRB0002', 'Moch. Alvian Hidayatulloh membeli Kertas Hvs sebanyak 5 dengan total harga Rp. 500.00', 'pg003', 'SP001', 'BG008', 5, 500, '2022-12-10 10:12:04'),
('TRB0003', 'Moch. Alvian Hidayatulloh membeli Kertas Folio sebanyak 5 dengan total harga Rp. 500.00', 'pg003', 'SP001', 'BG007', 5, 500, '2022-12-10 15:23:37'),
('TRB0004', 'Syamaidzar Adani Syah membeli Teh Pucuk sebanyak 5 dengan total harga Rp. 10,000.00', 'pg003', 'SP003', 'BG003', 5, 10000, '2022-12-11 13:25:22'),
('TRB0005', 'Syamaidzar Adani Syah membeli Kertas Hvs sebanyak 10 dengan total harga Rp. 1,000.00', 'pg003', 'SP003', 'BG008', 10, 1000, '2022-12-11 20:56:38'),
('TRB0006', 'Farid Kurniawan membeli Aice Ice Cream Cookies sebanyak 1 dengan total harga Rp. 5,000.00', 'pg003', 'SP007', 'BG034', 1, 5000, '2022-12-13 09:51:00'),
('TRB0007', 'Wahyu Bagas Prastyo membeli Good Time Cookies sebanyak 1 dengan total harga Rp. 5,000.00', 'pg003', 'SP010', 'BG033', 1, 5000, '2022-12-13 09:51:37'),
('TRB0008', 'Farid Kurniawan membeli Spidol Hitam sebanyak 1 dengan total harga Rp. 2,000.00', 'pg003', 'SP007', 'BG010', 1, 2000, '2022-12-13 12:07:05');

--
-- Triggers `transaksi_beli`
--
DELIMITER $$
CREATE TRIGGER `insert_saldo_beli` AFTER INSERT ON `transaksi_beli` FOR EACH ROW BEGIN
	SELECT jumlah_saldo INTO @saldo FROM saldo WHERE id_saldo = 'S001';
	SELECT total_hrg INTO @total_hrg FROM transaksi_beli ORDER BY id_tr_beli DESC LIMIT 0,1;
	UPDATE saldo SET jumlah_saldo = (@saldo - @total_hrg) WHERE id_saldo = 'S001';
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `insert_stok_beli` AFTER INSERT ON `transaksi_beli` FOR EACH ROW BEGIN
	SELECT id_barang INTO @id_barang FROM transaksi_beli ORDER BY id_tr_beli DESC LIMIT 0,1;
SELECT jumlah_brg INTO @total_barang FROM transaksi_beli ORDER BY id_tr_beli DESC LIMIT 0,1;
SELECT stok INTO @stok FROM barang WHERE id_barang = @id_barang;
UPDATE barang SET stok = (@stok + @total_barang) WHERE id_barang = @id_barang;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `transaksi_jual`
--

CREATE TABLE IF NOT EXISTS `transaksi_jual` (
  `id_tr_jual` varchar(7) NOT NULL,
  `nama_tr_jual` text DEFAULT NULL,
  `id_karyawan` varchar(5) DEFAULT NULL,
  `id_pembeli` varchar(5) NOT NULL,
  `id_barang` varchar(6) NOT NULL,
  `jumlah_brg` int(5) NOT NULL,
  `total_hrg` int(11) NOT NULL,
  `tanggal` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_tr_jual`),
  KEY `id_pembeli` (`id_pembeli`),
  KEY `id_barang` (`id_barang`),
  KEY `id_karyawan` (`id_karyawan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaksi_jual`
--

INSERT INTO `transaksi_jual` (`id_tr_jual`, `nama_tr_jual`, `id_karyawan`, `id_pembeli`, `id_barang`, `jumlah_brg`, `total_hrg`, `tanggal`) VALUES
('TRJ0001', 'menjual indomilk', 'PG003', 'PB003', 'BG006', 4, 10000, '2022-12-08 21:36:55'),
('TRJ0002', 'Abdulloh Kafabi membeli Pulpen Snowman sebanyak 5 dengan total harga Rp. 10,000.00', 'pg003', 'PB002', 'BG009', 5, 10000, '2022-12-10 15:22:57'),
('TRJ0003', 'Aga Deva Kharisma membeli Kertas Hvs sebanyak 5 dengan total harga Rp. 1,250.00', 'pg003', 'PB005', 'BG008', 5, 1250, '2022-12-11 13:12:33'),
('TRJ0004', 'Afif Fitra Nugroho membeli Kertas Folio sebanyak 5 dengan total harga Rp. 1,250.00', 'pg003', 'PB004', 'BG007', 5, 1250, '2022-12-11 20:56:46'),
('TRJ0005', 'Abdul Fattah Burhanuddin membeli Spidol Hitam sebanyak 4 dengan total harga Rp. 10,000.00', 'pg003', 'PB001', 'BG010', 4, 10000, '2022-12-12 08:02:17'),
('TRJ0006', 'Abdul Fattah Burhanuddin membeli aqua cleo sebanyak 1 dengan total harga Rp. 2,500.00', 'pg003', 'PB001', 'BG001', 1, 2500, '2022-12-12 08:09:04'),
('TRJ0007', 'Ahmad Fauzi Waro membeli Indomilk sebanyak 1 dengan total harga Rp. 2,500.00', 'pg003', 'PB007', 'BG006', 1, 2500, '2022-12-12 14:09:20'),
('TRJ0008', 'Ahmad Riyanto membeli Teh Pucuk sebanyak 1 dengan total harga Rp. 4,000.00', 'pg003', 'PB008', 'BG003', 1, 4000, '2022-12-13 12:07:12'),
('TRJ0009', 'Ahmad Edy Syaputra membeli Kertas Hvs sebanyak 1 dengan total harga Rp. 250.00', 'pg003', 'PB006', 'BG008', 1, 250, '2022-12-14 18:35:56'),
('TRJ0010', 'Achmad Romadoni membeli Pulpen Snowman sebanyak 1 dengan total harga Rp. 2,000.00', 'pg003', 'PB003', 'BG009', 1, 2000, '2022-12-14 18:36:04'),
('TRJ0011', 'Abdul Fattah Burhanuddin membeli Teh Pucuk sebanyak 4 dengan total harga Rp. 16,000.00', 'pg003', 'PB001', 'BG003', 4, 16000, '2022-12-14 18:36:34');

--
-- Triggers `transaksi_jual`
--
DELIMITER $$
CREATE TRIGGER `delete_stok_jual` AFTER DELETE ON `transaksi_jual` FOR EACH ROW BEGIN
	SELECT id_barang INTO @id_barang FROM transaksi_jual ORDER BY id_tr_jual DESC LIMIT 0,1;
	SELECT jumlah_brg INTO @total_barang FROM transaksi_jual ORDER BY id_tr_jual DESC LIMIT 0,1;
	SELECT stok INTO @stok FROM barang WHERE id_barang = @id_barang;
UPDATE barang SET stok = (@stok + @total_barang) WHERE id_barang = @id_barang;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `insert_saldo_jual` AFTER INSERT ON `transaksi_jual` FOR EACH ROW BEGIN
	SELECT jumlah_saldo INTO @saldo FROM saldo WHERE id_saldo = 'S001';
	SELECT total_hrg INTO @total_hrg FROM transaksi_jual ORDER BY id_tr_jual DESC LIMIT 0,1;
	UPDATE saldo SET jumlah_saldo = (@saldo + @total_hrg) WHERE id_saldo = 'S001';
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `insert_stok_jual` AFTER INSERT ON `transaksi_jual` FOR EACH ROW BEGIN
	SELECT id_barang INTO @id_barang FROM transaksi_jual ORDER BY id_tr_jual DESC LIMIT 0,1;
	SELECT jumlah_brg INTO @total_barang FROM transaksi_jual ORDER BY id_tr_jual DESC LIMIT 0,1;
	SELECT stok INTO @stok FROM barang WHERE id_barang = @id_barang;
UPDATE barang SET stok = (@stok - @total_barang) WHERE id_barang = @id_barang;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id_user` varchar(5) NOT NULL,
  `password` varchar(100) NOT NULL,
  `level` enum('ADMIN','KARYAWAN','PEMBELI','SUPPLIER') NOT NULL,
  `salt` varchar(50) NOT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id_user`, `password`, `level`, `salt`) VALUES
('PB001', '12345', 'PEMBELI', ''),
('PB002', '12345', 'PEMBELI', ''),
('PB003', '12345', 'PEMBELI', ''),
('PB004', '12345', 'PEMBELI', ''),
('PB005', '12345', 'PEMBELI', ''),
('PB006', '12345', 'PEMBELI', ''),
('PB007', '12345', 'PEMBELI', ''),
('PB008', '12345', 'PEMBELI', ''),
('PB009', '12345', 'PEMBELI', ''),
('PB010', '12345', 'PEMBELI', ''),
('PG001', '12345678', 'ADMIN', ''),
('PG002', '12345', 'ADMIN', ''),
('PG003', '12345', 'KARYAWAN', ''),
('PG004', '12345', 'KARYAWAN', ''),
('SP001', '12345', 'SUPPLIER', ''),
('SP002', '12345', 'SUPPLIER', ''),
('SP003', '12345', 'SUPPLIER', ''),
('SP004', '12345', 'SUPPLIER', ''),
('SP005', '12345', 'SUPPLIER', ''),
('SP006', '12345', 'SUPPLIER', ''),
('SP007', '12345', 'SUPPLIER', ''),
('SP008', '12345', 'SUPPLIER', ''),
('SP009', '12345', 'SUPPLIER', ''),
('SP010', '12345', 'SUPPLIER', '');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `karyawan`
--
ALTER TABLE `karyawan`
  ADD CONSTRAINT `karyawan_ibfk_1` FOREIGN KEY (`id_karyawan`) REFERENCES `users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `pembeli`
--
ALTER TABLE `pembeli`
  ADD CONSTRAINT `pembeli_ibfk_1` FOREIGN KEY (`id_pembeli`) REFERENCES `users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `supplier`
--
ALTER TABLE `supplier`
  ADD CONSTRAINT `supplier_ibfk_1` FOREIGN KEY (`id_supplier`) REFERENCES `users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transaksi_beli`
--
ALTER TABLE `transaksi_beli`
  ADD CONSTRAINT `transaksi_beli_ibfk_1` FOREIGN KEY (`id_barang`) REFERENCES `barang` (`id_barang`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transaksi_beli_ibfk_2` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id_supplier`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transaksi_beli_ibfk_3` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transaksi_jual`
--
ALTER TABLE `transaksi_jual`
  ADD CONSTRAINT `transaksi_jual_ibfk_1` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transaksi_jual_ibfk_2` FOREIGN KEY (`id_pembeli`) REFERENCES `pembeli` (`id_pembeli`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transaksi_jual_ibfk_3` FOREIGN KEY (`id_barang`) REFERENCES `barang` (`id_barang`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
