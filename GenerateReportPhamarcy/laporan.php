<?php 
    include "config/koneksi.php";

?>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Laporan Penjualan</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
      crossorigin="anonymous"
    />
    <link rel="stylesheet" href="./style/global.css" />
  </head>
  <body>
    <main class="p-5 mx-auto mt-5 rounded">
      <div
        class="border-bottom border-5 pb-2 mb-3 d-flex justify-content-between align-items-center"
      >
        <h1 class="fw-bold">Laporan Penjualan</h1>
        <img
          src="./assets/my-logo.png"
          class="img-fluid img-thumbnail"
          width="80"
          alt="logo"
        />
      </div>

      <div>
        <table class="table table-borderless" style="width: max-content">
          <tr class="border-0">
            <th>Tipe Laporan</th>
            <td>:</td>
            <?php if(isset($_GET['tgl_awal']) && isset($_GET['tgl_akhir'])) : ?>
              <td>Range Waktu</td>
            <?php endif ?>
            <?php if(isset($_GET['kategori'])) : ?>
              <td>Kategori</td>
            <?php endif ?>
            <?php if(isset($_GET['pelanggan'])) : ?>
              <td>Pelanggan</td>
            <?php endif ?>
          </tr>
          <tr class="border-0">
            <th>Keterangan</th>
            <td>:</td>
            <?php if(isset($_GET['tgl_awal']) && isset($_GET['tgl_akhir'])) : ?>
              <td><?= $_GET['tgl_awal'] ?> s/d <?= $_GET['tgl_akhir'] ?></td>
            <?php endif ?>
            <?php if(isset($_GET['kategori'])) : ?>
              <td><?= $_GET['kategori'] ?></td>
            <?php endif ?>
            <?php if(isset($_GET['pelanggan'])) : ?>
              <?php 
                  $result = mysqli_query($con, "SELECT * FROM tb_pelanggan WHERE id_pelanggan=" . $_GET['pelanggan']);
                  while($pelanggan=mysqli_fetch_array($result)) {
              ?>
              <td><?= $pelanggan['nama_pelanggan'] ?> </br> <?= $pelanggan['email'] ?></td>
              <?php } ?>
            <?php endif ?>
          </tr>
        </table>
      </div>

      
      <!-- BY DATE -->
      <?php if(isset($_GET['tgl_awal']) && isset($_GET['tgl_akhir'])) : ?>
        <div class="mt-5">
          <table class="table table-striped">
            <thead>
              <tr>
                <th scope="col">No</th>
                <th scope="col">Kode Transaksi</th>
                <th scope="col">Nama Pelanggan</th>
                <th scope="col">Tgl Transaksi</th>
                <th scope="col">Total</th>
              </tr>
            </thead>
            <tbody>
              <?php 
                  $no=0;
                  $total_pendapatan = 0;
                  $a = mysqli_query($con,"SELECT * FROM tb_transaksi as ts INNER JOIN tb_pelanggan as tp  ON ts.id_pelanggan = tp.id_pelanggan WHERE ts.tgl_transaksi BETWEEN '" . $_GET['tgl_awal'] . "' AND '" . $_GET['tgl_awal'] . "'");
                  
                  while($tb_transaksi=mysqli_fetch_array($a)) {
                  $total_pendapatan += $tb_transaksi['total_pembelian'];
                  $no++
              ?>
              <tr>
                <td><?= $no ?></td>
                <td><?= $tb_transaksi['kode_transaksi'] ?></td>
                <td><?= $tb_transaksi['nama_pelanggan'] ?></td>
                <td><?= $tb_transaksi['tgl_transaksi'] ?></td>
                <td><?= $tb_transaksi['total_pembelian'] ?></td>
              </tr>
              <tr>
                <td></td>
                <td colspan="4">
                  <table class="table mb-0">
                    <thead>
                      <tr>
                        <td scope="col">No</td>
                        <td scope="col">Nama Barang</td>
                        <td scope="col">Harga</td>
                        <td scope="col">Qty</td>
                        <td scope="col">Total</td>
                      </tr>
                      <?php 
                          $no=0;
                          $query = "SELECT * FROM tb_entry_transaksi as te WHERE te.kode_transaksi = " . "'" . $tb_transaksi['kode_transaksi'] . "'" ;
                          $entry = mysqli_query($con, $query);

                          while($tb_item=mysqli_fetch_array($entry)) :
                          $no++
                      ?>
                        <tr>
                          <td><?= $no ?></td>
                          <td><?= $tb_item['nama_obat'] ?></td>
                          <td><?= $tb_item['harga_item'] ?></td>
                          <td><?= $tb_item['quantity'] ?></td>
                          <td><?= $tb_item['total'] ?></td>
                        </tr>
                      <?php endwhile ?>
                    </thead>
                  </table>
                </td>
              </tr>
              <?php } ?>
            </tbody>
          </table>
        </div>

        <div class="d-flex mt-5">
          <table style="width: max-content">
            <tr>
              <th class="text-start fs-5">Total Pendapatan</th>
              <td class="text-end text-center" style="width: 25px">:</td>
              <td class="text-end fs-5"><?php echo $total_pendapatan ?></td>
            </tr>
          </table>
        </div>
      <?php endif ?>

      <!-- BY KATEGORI -->
      <?php if(isset($_GET['kategori'])) : ?>
        <div class="mt-5">
          <table class="table table-striped">
            <thead>
              <tr>
                <th scope="col">No</th>
                <th scope="col">Nama Obat</th>
                <th scope="col">Kategori</th>
                <th scope="col">Total</th>
              </tr>
            </thead>
            <tbody>
              <?php 
                  $no=0;
                  $total_pendapatan = 0;
                  $a = mysqli_query($con,"SELECT tob.nama_obat, tob.kategori, sum(te.total) as 'total_pembelian' FROM tb_entry_transaksi as te LEFT JOIN tb_obat as tob ON  te.kode_obat = tob.kode_obat WHERE tob.kategori = '" . $_GET['kategori'] . "' GROUP BY tob.nama_obat");
                  
                  while($tb_transaksi=mysqli_fetch_array($a)) {
                  $total_pendapatan += $tb_transaksi['total_pembelian'];
                  $no++
              ?>
              <tr>
                <td><?= $no ?></td>
                <td><?= $tb_transaksi['nama_obat'] ?></td>
                <td><?= $tb_transaksi['kategori'] ?></td>
                <td><?= $tb_transaksi['total_pembelian'] ?></td>
              </tr>
              <?php } ?>
            </tbody>
          </table>
        </div>

        <div class="d-flex mt-5">
          <table style="width: max-content">
            <tr>
              <th class="text-start fs-5">Total Pendapatan</th>
              <td class="text-end text-center" style="width: 25px">:</td>
              <td class="text-end fs-5"><?php echo $total_pendapatan ?></td>
            </tr>
          </table>
        </div>
      <?php endif ?>

      <!-- BY PELANGGAN -->
      <?php if(isset($_GET['pelanggan'])) : ?>
        <div class="mt-5">
          <table class="table table-striped">
            <thead>
              <tr>
                <th scope="col">No</th>
                <th scope="col">Kode Transaksi</th>
                <th scope="col">Nama Pelanggan</th>
                <th scope="col">Tgl Transaksi</th>
                <th scope="col">Total</th>
              </tr>
            </thead>
            <tbody>
              <?php 
                  $no=0;
                  $total_pendapatan = 0;
                  $a = mysqli_query($con, "SELECT * FROM tb_transaksi as ts INNER JOIN tb_pelanggan as tp  ON ts.id_pelanggan = tp.id_pelanggan WHERE ts.id_pelanggan=" . $_GET['pelanggan']);
                  
                  while($tb_transaksi=mysqli_fetch_array($a)) {
                  $total_pendapatan += $tb_transaksi['total_pembelian'];
                  $no++
              ?>
              <tr>
                <td><?= $no ?></td>
                <td><?= $tb_transaksi['kode_transaksi'] ?></td>
                <td><?= $tb_transaksi['nama_pelanggan'] ?></td>
                <td><?= $tb_transaksi['tgl_transaksi'] ?></td>
                <td><?= $tb_transaksi['total_pembelian'] ?></td>
              </tr>
              <tr>
                <td></td>
                <td colspan="4">
                  <table class="table mb-0">
                    <thead>
                      <tr>
                        <td scope="col">No</td>
                        <td scope="col">Nama Barang</td>
                        <td scope="col">Harga</td>
                        <td scope="col">Qty</td>
                        <td scope="col">Total</td>
                      </tr>
                      <?php 
                          $no=0;
                          $query = "SELECT * FROM tb_entry_transaksi as te WHERE te.kode_transaksi = " . "'" . $tb_transaksi['kode_transaksi'] . "'" ;
                          $entry = mysqli_query($con, $query);

                          while($tb_item=mysqli_fetch_array($entry)) :
                          $no++
                      ?>
                        <tr>
                          <td><?= $no ?></td>
                          <td><?= $tb_item['nama_obat'] ?></td>
                          <td><?= $tb_item['harga_item'] ?></td>
                          <td><?= $tb_item['quantity'] ?></td>
                          <td><?= $tb_item['total'] ?></td>
                        </tr>
                      <?php endwhile ?>
                    </thead>
                  </table>
                </td>
              </tr>
              <?php } ?>
            </tbody>
          </table>
        </div>

        <div class="d-flex mt-5">
          <table style="width: max-content">
            <tr>
              <th class="text-start fs-5">Total Pendapatan</th>
              <td class="text-end text-center" style="width: 25px">:</td>
              <td class="text-end fs-5"><?php echo $total_pendapatan ?></td>
            </tr>
          </table>
        </div>
      <?php endif ?>


      <div class="text-center my-5">
        <p class="fw-bold fst-italic">
          Laporan Penjualan Apotek Pharmacy - Asep Supriyadi
        </p>
      </div>
    </main>

    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
      crossorigin="anonymous"
    ></script>
  </body>
</html>
