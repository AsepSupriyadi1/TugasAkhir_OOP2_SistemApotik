<?php 
    include "config/koneksi.php";
?>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Faktur Penjualan - <?= $_GET['fk'] ?></title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
      crossorigin="anonymous"
    />
    <link rel="stylesheet" href="./style/global.css" />
  </head>
  <body onload="window.print()">
    <main class="p-5 mx-auto mt-5 rounded">
      <div class="border-bottom border-5 pb-2 mb-3 d-flex justify-content-between align-items-center">
        <h1 class="fw-bold">INVOICE</h1>
        <img src="./assets/my-logo.png" class="img-fluid img-thumbnail" width="80" alt="logo">
      </div>

      <div>
        <?php 
            $no=0;
            $a = mysqli_query($con,"SELECT * FROM tb_transaksi WHERE kode_transaksi = '$_GET[fk]'");
            while($r=mysqli_fetch_array($a)){
            $no++
        ?>
          <table style="width: 100%">
            <tr>
              <th class="text-start">Kepada:</th>
              <th class="text-end">Tanggal:</th>
            </tr>


            <?php 
              $query = "SELECT * FROM tb_pelanggan WHERE id_pelanggan = " . $r['id_pelanggan'];

              $a = mysqli_query($con, $query);
              while($UserResult=mysqli_fetch_array($a)){
              $no++
            ?>
              <tr>
                <td class="text-start"><?= $UserResult['nama_pelanggan'] ?></td>
                <td class="text-end"><?= $r['tgl_transaksi'] ?></td>
              </tr>
              <tr>
                <td class="text-start" colspan="2"><?= $UserResult['email'] ?></td>
              </tr>
            <?php } ?>

            <tr>
              <th class="text-end" colspan="2">No Invoice:</th>
            </tr>
            <tr>
              <td class="text-end" colspan="2"><?= $r['kode_transaksi'] ?></td>
            </tr>
          </table>
        <?php } ?>
      </div>

      <div class="mt-5">
        <table class="table table-striped">
          <thead>
            <tr>
              <th scope="col">No</th>
              <th scope="col">Keterangan</th>
              <th scope="col">Harga</th>
              <th scope="col">Jumlah</th>
              <th scope="col">Total</th>
            </tr>
          </thead>
          <tbody>
            <?php 
                $no=0;
                $a = mysqli_query($con,"SELECT * FROM tb_entry_transaksi WHERE kode_transaksi = '$_GET[fk]'");
                while($r=mysqli_fetch_array($a)){
                $no++
            ?>
              <tr>
                <td><?php echo $no ?></td>
                <td><?php echo $r['nama_obat'] ?></td>
                <td><?php echo $r['harga_item'] ?></td>
                <td><?php echo $r['quantity'] ?></td>
                <td><?php echo $r['total'] ?></td>
              </tr> 
            <?php } ?>
          </tbody>
        </table>
      </div>

      <div class="d-flex justify-content-end mt-4">
        <table style="width: max-content;">
          <?php 
                $no=0;
                $a = mysqli_query($con,"SELECT * FROM tb_transaksi WHERE kode_transaksi = '$_GET[fk]'");
                while($r=mysqli_fetch_array($a)){
                $no++
          ?>
            <tr>
              <th class="text-start">Total</th>
              <td class="text-end text-center" style="width: 25px;">:</td>
              <td class="text-end"><?php echo $r['total_pembelian'] ?></td>
            </tr>
            <tr>
              <td class="text-start">Tunai</td>
              <td class="text-end text-center" style="width: 25px;">:</td>
              <td class="text-end"><?php echo $r['total_dibayarkan'] ?></td>
            </tr>
            <tr>
              <td class="text-start">Kembalian</td>
              <td class="text-end text-center" style="width: 25px;">:</td>
              <td class="text-end"><?php echo $r['total_kembalian'] ?></td>
            </tr>
          <?php } ?>
        </table>
      </div>


      <div class="text-center my-4">
        <p class="fw-bold fst-italic">Terimakasih atas pembelian anda</p>
      </div>
    </main>

    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
      crossorigin="anonymous"
    ></script>
  </body>
</html>
