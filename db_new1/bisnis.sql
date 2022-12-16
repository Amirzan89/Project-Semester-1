-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 16, 2022 at 01:49 PM
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
-- Database: `bisnis`
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
('BG001', 'aqua cleo', 'MINUMAN', 71, 2000, 2500),
('BG003', 'Teh Pucuk', 'MINUMAN', 10, 2000, 4000),
('BG006', 'Indomilk', 'MINUMAN', 16, 2000, 2500),
('BG007', 'Kertas Folio', 'ATK', 322, 100, 250),
('BG008', 'Kertas Hvs', 'ATK', 610, 100, 250),
('BG009', 'Pulpen Snowman', 'ATK', 99, 1000, 2000),
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

-- --------------------------------------------------------

--
-- Table structure for table `karyawan`
--

CREATE TABLE IF NOT EXISTS `karyawan` (
  `id_karyawan` varchar(5) NOT NULL,
  `nama_karyawan` varchar(50) NOT NULL,
  `no_telp` varchar(15) NOT NULL,
  `alamat` text NOT NULL,
  PRIMARY KEY (`id_karyawan`),
  KEY `id_karyawan` (`id_karyawan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `karyawan`
--

INSERT INTO `karyawan` (`id_karyawan`, `nama_karyawan`, `no_telp`, `alamat`) VALUES
('PG001', 'Developer', '085655864624', 'Nganjuk, Jawa Timur, Indonesia'),
('PG002', 'Achmad Baihaqi', '085655864624', 'Jombang, Jawa Timur, Indonesia'),
('PG003', 'Amirzan Fikri Prasetyo', '085105304100', 'Jombang, Jawa Timur, Indonesia'),
('PG004', 'Mohammad Ilham Islamy', '085784626830', 'Nganjuk, Jawa Timur, Indonesia'),
('PG005', 'Dev paling ganteng', '08123435612', 'Planet Mars');

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
('S001', 1000000);

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
('SP010', 'Wahyu Bagas Prastyo', '081913236990', 'Nganju, Jawa Timur, Indonesia'),
('SP001', 'Moch. Alvian Hidayatulloh', '085990128912', 'Nganjuk, Jawa Timur, Indonesia'),
('SP002', 'Afrizal Wahyu Alkautsar ', '086790238923', 'Nganjuk, Jawa Timur, Indonesia'),
('SP003', 'Syamaidzar Adani Syah', '085690231830', 'Nganjuk, Jawa Timur, Indonesia'),
('SP004', 'Pramudya Putra Pratama', '081289378712', 'Jombang, Jawa Timur, Indonesia'),
('SP005', 'Syafrizal Wd Mahendra', '085690237823', 'Kediri, Jawa Timur, Indonesia'),
('SP008', 'M. Ferdiansyah', '085690238923', 'Jombang, Jawa Timur'),
('SP010', 'Amirzan Fikri Prasetyo', '085690238923', 'Jombang, Jawa Timur');

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
  `id_karyawan` varchar(5) NOT NULL,
  `total_hrg` int(11) NOT NULL,
  `pendapatan_bersih` int(15) NOT NULL,
  `tanggal` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_tr_jual`),
  KEY `id_karyawan` (`id_karyawan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id_user`, `password`, `level`) VALUES
('PB001', '$2a$15$rDIK1O/Rbi/eQ4trySxQYu5Xf89NDonuMgKnlcd5SNiRKT4lBP.vS', 'PEMBELI'),
('PB002', '$2a$15$dRRF5ecuh46iAXa6NaLhOOGCSODT3KjbU1voWbOzPar4DwuWihypW', 'PEMBELI'),
('PB003', '$2a$15$6PKcyT8ECkdz3OTVRet59e4E1HRS16yEDbOtV9.nKV/vsfjmjCfVC', 'PEMBELI'),
('PB004', '$2a$15$G1PF2okdB2JqWijYx5CxouzKPtXCPEkCEOFX6ZgyN6rT/mnP.IfSC', 'PEMBELI'),
('PB005', '$2a$15$r5ZAQ2nZNi6.QuwRB.okNOUaWvRV.HNmXIKLTJa02EuaGLJAL5yjG', 'PEMBELI'),
('PB006', '$2a$15$rIjmKDrYebjWUH35GrskNen1Ao95lFLNVLiLo0LVEJtuM4eNtTv1u', 'PEMBELI'),
('PB007', '$2a$15$yU8H46jszNmE1KmcvwetKeiVf7PtaNNkIy0zEZW5rFCyiCOgjKnjS', 'PEMBELI'),
('PB008', '$2a$15$JFzsDYcXVV.AxZPYCEUByuJ.VQ42QWQQ5Zzej1wvFfpXsb4nt5reC', 'PEMBELI'),
('PB009', '$2a$15$hDPvR8lCD8Q1dBojfYrnxOQyBKlIUjAswUILPanm1QV03aifSUAVC', 'PEMBELI'),
('PB010', '$2a$15$5wg3girq2Wn7GnPf.BKXH.ioSprIyzep3d.WCz.sCgk1txOlfJvpy', 'PEMBELI'),
('PG001', '$2a$15$8HrvpgFXQeBDHXoRrwkIr.tfQ.fQe93fUiu42oJPrEppFUOTIiQOe', 'ADMIN'),
('PG002', '$2a$15$NLrT04XuVIn8XTpWwePPO.BaKKgrlM32FociSRAr5H48ok0x1cJAO', 'ADMIN'),
('PG003', '$2a$15$edYOnmz1Jj1n3ybI3OFbvOiRCWFlX4H1FBUvHTqtPQIHKL2UoZsTO', 'KARYAWAN'),
('PG004', '$2a$15$SmGKvqyTENl/WtWWxFep1OnTzX27y.YxWNjpiJsSHLxzgQQz1GMRa', 'KARYAWAN'),
('PG005', '$2a$15$Kt0.7PEi7X2GjUZ31R9ab.jihNj.EBeE43cMTip/xXDFDNHYlo772', 'ADMIN'),
('SP001', '$2a$15$rAd1qtbd9j4k5NbZwQ5vIep8Lfabe6EXM0aPgbBFHNcEoItvR2YhO', 'SUPPLIER'),
('SP002', '$2a$15$Hkc6zES2Hbo1SWytpvvYP.DVI7u0iAotKcSBgl/PHVwklzYRGc0l.', 'SUPPLIER'),
('SP003', '$2a$15$p9WGBUOuTGHuB9XvGZ50oeyZ0izwYi3Ig1II1yGHbRLi7jcPoeE7S', 'SUPPLIER'),
('SP004', '$2a$15$HyoGNCHAbOpFCOXUa38X1.6571Iaasmr42dCnIgyeHtBKlKt6ocgu', 'SUPPLIER'),
('SP005', '$2a$15$kF2gVwknvCwIsX7ngZdCpOvCbLUy5fO/mz80lbfkoNsoUw7Rdu6cO', 'SUPPLIER'),
('SP006', '$2a$15$hjtg/eI931bWgk2x0IVGkeg12ySVLqAdQLHc3nkHl5yOGxrS3q/Uu', 'SUPPLIER'),
('SP007', '$2a$15$sqgWjnSmAZLQr05CteFgzOMWiQLYFCrxpTvRXjHoimnr.ttWsNvqi', 'SUPPLIER'),
('SP008', '$2a$15$NeC.rDL76jbQNkw.uSzIxeNg7laWbHztzk/sJLRG5cHj75iGGIPwe', 'SUPPLIER'),
('SP009', '$2a$15$Y0IoqVRVeHTIo84z2LfFbOpjN47x6BgmB01.jwtSLpgaufNGyclqu', 'SUPPLIER'),
('SP010', '$2a$15$2BSuAfYH.B0iONxgpCR.SOlhxcUP2nbb5lW8rHosAyTmMp9/.daFu', 'SUPPLIER');

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
-- Constraints for table `karyawan`
--
ALTER TABLE `karyawan`
  ADD CONSTRAINT `karyawan_ibfk_1` FOREIGN KEY (`id_karyawan`) REFERENCES `users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `supplier`
--
ALTER TABLE `supplier`
  ADD CONSTRAINT `supplier_ibfk_1` FOREIGN KEY (`id_supplier`) REFERENCES `users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

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
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
