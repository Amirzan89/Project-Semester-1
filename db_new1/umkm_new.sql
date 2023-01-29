-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 29, 2023 at 01:59 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `umkm`
--
CREATE DATABASE umkm_barang;
USE umkm_barang;
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
  KEY `fk_detail2` (`id_tr_beli`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `detail_transaksi_beli`
--

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
  KEY `fk_detail1` (`id_tr_jual`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `detail_transaksi_jual`
--

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
('KY001', 'Admin', '085105304100', 'Nganjuk,Jawa Timur,Indonesia');

-- --------------------------------------------------------

--
-- Table structure for table `migrations`
--

CREATE TABLE IF NOT EXISTS `migrations` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `migration` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `batch` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `migrations`
--

INSERT INTO `migrations` (`id`, `migration`, `batch`) VALUES
(1, '2023_01_14_120850_create_table', 1);

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
('S001', 10000000);

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

-- --------------------------------------------------------

--
-- Table structure for table `table`
--

CREATE TABLE IF NOT EXISTS `table` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `transaksi_beli`
--

CREATE TABLE IF NOT EXISTS `transaksi_beli` (
  `id_tr_beli` varchar(7) NOT NULL,
  `id_karyawan` varchar(5) NOT NULL,
  `nama_karyawan` varchar(50) NOT NULL,
  `total_hrg` int(11) NOT NULL,
  `tanggal` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_tr_beli`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaksi_beli`
--

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
  `nama_karyawan` varchar(50) NOT NULL,
  `total_hrg` int(11) NOT NULL,
  `keuntungan` int(15) NOT NULL,
  `tanggal` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_tr_jual`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaksi_jual`
--

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
('Admin', '$2a$15$JYACUxM792fwXX.bUb8L..LKJU3lLB/7N1jXw3SwPDy/BS7sLCdoO', 'ADMIN', 'KY001');
--
-- Constraints for dumped tables
--

--
-- Constraints for table `detail_transaksi_beli`
--
ALTER TABLE `detail_transaksi_beli`
  ADD CONSTRAINT `fk_detail2` FOREIGN KEY (`id_tr_beli`) REFERENCES `transaksi_beli` (`id_tr_beli`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `detail_transaksi_jual`
--
ALTER TABLE `detail_transaksi_jual`
  ADD CONSTRAINT `fk_detail1` FOREIGN KEY (`id_tr_jual`) REFERENCES `transaksi_jual` (`id_tr_jual`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `fk_user1` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`) ON DELETE CASCADE ON UPDATE CASCADE;
SET FOREIGN_KEY_CHECKS=1;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
