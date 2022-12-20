-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 20, 2022 at 06:41 AM
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
-- Database: `baru_bisnis`
--
CREATE DATABASE IF NOT EXISTS `baru_bisnis` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `baru_bisnis`;

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
('BG001', 'aqua cleo', 'MINUMAN', 111, 2000, 2500),
('BG003', 'Teh Pucuk', 'MINUMAN', 80, 2000, 4000),
('BG006', 'Indomilk', 'MINUMAN', 226, 2000, 2500),
('BG007', 'Kertas Folio', 'ATK', 342, 100, 250),
('BG008', 'Kertas Hvs', 'ATK', 610, 100, 250),
('BG009', 'Pulpen Snowman', 'ATK', 19, 1000, 2000),
('BG010', 'Spidol Hitam', 'ATK', 28, 2000, 2500),
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
('BG033', 'Good Time Cookies', 'SNACK', 37, 5000, 7500),
('BG034', 'Aice Ice Cream Cookies', 'MINUMAN', 22, 5000, 6000),
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
-- Table structure for table `detail_transaksi_beli`
--

CREATE TABLE IF NOT EXISTS `detail_transaksi_beli` (
  `id_tr_beli` varchar(7) NOT NULL,
  `id_supplier` varchar(5) NOT NULL,
  `nama_supplier` varchar(50) NOT NULL,
  `id_barang` varchar(6) NOT NULL,
  `nama_barang` varchar(30) NOT NULL,
  `jenis_barang` enum('SNACK','MAKANAN','MINUMAN','ATK') NOT NULL,
  `harga_beli` int(11) NOT NULL,
  `jumlah` int(5) NOT NULL,
  `total_harga` int(12) NOT NULL,
  UNIQUE KEY `id_tr_beli` (`id_tr_beli`,`id_barang`),
  KEY `id_supplier` (`id_supplier`),
  KEY `detail_tr_beli2` (`id_barang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `detail_transaksi_beli`
--

INSERT INTO `detail_transaksi_beli` (`id_tr_beli`, `id_supplier`, `nama_supplier`, `id_barang`, `nama_barang`, `jenis_barang`, `harga_beli`, `jumlah`, `total_harga`) VALUES
('TRB0001', 'SP002', 'Syafrizal Wd Mahendra', 'BG003', 'Teh Pucuk', 'MINUMAN', 2000, 30, 60000),
('TRB0001', 'SP002', 'Syafrizal Wd Mahendra', 'BG006', 'Indomilk', 'MINUMAN', 2000, 30, 60000),
('TRB0001', 'SP002', 'Syafrizal Wd Mahendra', 'BG007', 'Kertas Folio', 'ATK', 100, 20, 2000),
('TRB0002', 'SP003', 'Syamaidzar Adani Syah', 'BG001', 'Aqua Cleo', 'MINUMAN', 2000, 30, 60000),
('TRB0002', 'SP003', 'Syamaidzar Adani Syah', 'BG006', 'Indomilk', 'MINUMAN', 2000, 30, 60000),
('TRB0003', 'SP001', 'Moch. Alvian Hidayatulloh', 'BG003', 'Teh Pucuk', 'MINUMAN', 2000, 40, 80000),
('TRB0004', 'SP002', 'Syafrizal Wd Mahendra', 'BG001', 'Aqua Cleo', 'MINUMAN', 2000, 40, 80000),
('TRB0005', 'SP003', 'Syamaidzar Adani Syah', 'BG003', 'Teh Pucuk', 'MINUMAN', 2000, 30, 60000);

--
-- Triggers `detail_transaksi_beli`
--
DELIMITER $$
CREATE TRIGGER `insert_stok_beli` AFTER INSERT ON `detail_transaksi_beli` FOR EACH ROW BEGIN
	SELECT id_barang INTO @id_barang FROM detail_transaksi_beli ORDER BY id_tr_beli DESC LIMIT 0,1;
SELECT jumlah INTO @total_barang FROM detail_transaksi_beli ORDER BY id_tr_beli DESC LIMIT 0,1;
SELECT stok INTO @stok FROM barang WHERE id_barang = @id_barang;
UPDATE barang SET stok = (@stok + @total_barang) WHERE id_barang = @id_barang;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `detail_transaksi_jual`
--

CREATE TABLE IF NOT EXISTS `detail_transaksi_jual` (
  `id_tr_jual` varchar(7) NOT NULL,
  `id_barang` varchar(6) NOT NULL,
  `nama_barang` varchar(30) NOT NULL,
  `jenis_barang` enum('SNACK','MAKANAN','MINUMAN','ATK') NOT NULL,
  `harga` int(11) NOT NULL,
  `jumlah` int(5) NOT NULL,
  `total_harga` int(12) NOT NULL,
  KEY `id_tr_jual` (`id_tr_jual`,`id_barang`),
  KEY `detail_tr_jual1` (`id_barang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `detail_transaksi_jual`
--

INSERT INTO `detail_transaksi_jual` (`id_tr_jual`, `id_barang`, `nama_barang`, `jenis_barang`, `harga`, `jumlah`, `total_harga`) VALUES
('TRJ0001', 'BG009', 'Pulpen Snowman', 'ATK', 2000, 40, 80000),
('TRJ0001', 'BG003', 'Teh Pucuk', 'MINUMAN', 4000, 40, 80000);

--
-- Triggers `detail_transaksi_jual`
--
DELIMITER $$
CREATE TRIGGER `insert_stok_jual` AFTER INSERT ON `detail_transaksi_jual` FOR EACH ROW BEGIN
	SELECT id_barang INTO @id_barang FROM detail_transaksi_jual ORDER BY id_tr_jual DESC LIMIT 0,1;
	SELECT jumlah INTO @total_barang FROM detail_transaksi_jual ORDER BY id_tr_jual DESC LIMIT 0,1;
	SELECT stok INTO @stok FROM barang WHERE id_barang = @id_barang;
UPDATE barang SET stok = (@stok - @total_barang) WHERE id_barang = @id_barang;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `karyawan`
--

CREATE TABLE IF NOT EXISTS `karyawan` (
  `id_karyawan` varchar(5) NOT NULL,
  `nama_karyawan` varchar(50) NOT NULL,
  `no_telp` varchar(15) NOT NULL,
  `alamat` text NOT NULL,
  PRIMARY KEY (`id_karyawan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `karyawan`
--

INSERT INTO `karyawan` (`id_karyawan`, `nama_karyawan`, `no_telp`, `alamat`) VALUES
('KY001', 'Admin Paling Ganteng', '085105304100', 'Jombang,Jawa Timur,Indonesia'),
('KY002', 'Amirzan Fikri Prasetyo', '085105304100', 'Jombang,Jawa Timur,Indonesia'),
('KY003', 'Nira Ayuhana Nurlitha', '085733687573', 'Nganjuk,Jawa Timur,Indonesia'),
('KY004', 'Elsa Septiana Rif\'at', '081333940999', 'Blitar,Jawa Timur,Indonesia'),
('KY005', 'Dewi Novasari', '083866544654', 'Nganjuk,Jawa Timur,Indonesia');

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
('S001', 456000);

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE IF NOT EXISTS `supplier` (
  `id_supplier` varchar(5) NOT NULL,
  `nama_supplier` varchar(50) NOT NULL,
  `no_telp` varchar(15) NOT NULL,
  `alamat` text NOT NULL,
  PRIMARY KEY (`id_supplier`)
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
  `id_karyawan` varchar(5) NOT NULL,
  `total_hrg` int(11) NOT NULL,
  `tanggal` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_tr_beli`),
  KEY `id_petugas` (`id_karyawan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaksi_beli`
--

INSERT INTO `transaksi_beli` (`id_tr_beli`, `id_karyawan`, `total_hrg`, `tanggal`) VALUES
('TRB0001', 'KY001', 122000, '2022-12-20 12:34:08'),
('TRB0002', 'KY001', 120000, '2022-12-20 12:34:21'),
('TRB0003', 'KY001', 80000, '2022-12-20 12:35:33'),
('TRB0004', 'KY001', 80000, '2022-12-20 12:35:39'),
('TRB0005', 'KY001', 60000, '2022-12-20 12:35:50');

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

-- --------------------------------------------------------

--
-- Table structure for table `transaksi_jual`
--

CREATE TABLE IF NOT EXISTS `transaksi_jual` (
  `id_tr_jual` varchar(7) NOT NULL,
  `id_karyawan` varchar(5) NOT NULL,
  `total_hrg` int(11) NOT NULL,
  `keuntungan` int(15) NOT NULL,
  `tanggal` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_tr_jual`),
  KEY `id_karyawan` (`id_karyawan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaksi_jual`
--

INSERT INTO `transaksi_jual` (`id_tr_jual`, `id_karyawan`, `total_hrg`, `keuntungan`, `tanggal`) VALUES
('TRJ0001', 'KY001', 160000, 120000, '2022-12-20 12:40:28');

--
-- Triggers `transaksi_jual`
--
DELIMITER $$
CREATE TRIGGER `insert_saldo_jual` AFTER INSERT ON `transaksi_jual` FOR EACH ROW BEGIN
	SELECT jumlah_saldo INTO @saldo FROM saldo WHERE id_saldo = 'S001';
	SELECT total_hrg INTO @total_hrg FROM transaksi_jual ORDER BY id_tr_jual DESC LIMIT 0,1;
	UPDATE saldo SET jumlah_saldo = (@saldo + @total_hrg) WHERE id_saldo = 'S001';
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `username` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `level` enum('ADMIN','KARYAWAN') NOT NULL,
  `id_karyawan` varchar(5) NOT NULL,
  PRIMARY KEY (`username`),
  KEY `fk_user1` (`id_karyawan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`username`, `password`, `level`, `id_karyawan`) VALUES
('adminGanteng', '$2a$18$cBVu5Jj78Lgdk720doi07uJFSR2CqYvMgguN7mlbBy.kxOZKYDEr.', 'ADMIN', 'KY001'),
('Amirzan12', '$2a$18$M8N4AHbcyUGAYTEWibZGJ.Nj3k6y5iC8YlrIJW97kVYH8OcyVSZye', 'KARYAWAN', 'KY002'),
('Dewi11', '$2a$18$mVBjxhhAfFjkEtAMILLE8eibAQzuL81Eq97OSAgS3yYO35Cod.k4.', 'KARYAWAN', 'KY005'),
('ELsa02', '$2a$18$bvbu4tPthmJihPkGDtyYZe5TSypND8OpSTrJuWYS53Qxe6FLxDSuW', 'KARYAWAN', 'KY004'),
('Nira19', '$2a$18$/Fp2X.XgW.l5CSaMhfM5su/sikY/.Wuu9wYbohTApCOSQiw3N5db2', 'KARYAWAN', 'KY003');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `detail_transaksi_beli`
--
ALTER TABLE `detail_transaksi_beli`
  ADD CONSTRAINT `detail_tr_beli1` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id_supplier`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `detail_tr_beli2` FOREIGN KEY (`id_barang`) REFERENCES `barang` (`id_barang`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `detail_tr_beli3` FOREIGN KEY (`id_tr_beli`) REFERENCES `transaksi_beli` (`id_tr_beli`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `detail_transaksi_jual`
--
ALTER TABLE `detail_transaksi_jual`
  ADD CONSTRAINT `detail_tr_jual1` FOREIGN KEY (`id_barang`) REFERENCES `barang` (`id_barang`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `detail_tr_jual2` FOREIGN KEY (`id_tr_jual`) REFERENCES `transaksi_jual` (`id_tr_jual`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transaksi_beli`
--
ALTER TABLE `transaksi_beli`
  ADD CONSTRAINT `transaksi_beli_ibfk_3` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `transaksi_jual`
--
ALTER TABLE `transaksi_jual`
  ADD CONSTRAINT `transaksi_jual_ibfk_1` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `fk_user1` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
