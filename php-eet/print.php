<style type="text/css">
.tg  {border-collapse:collapse;border-spacing:0;border-width:1px;border-style:solid;}
.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:0px;overflow:hidden;word-break:normal;}
.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:0px;overflow:hidden;word-break:normal;}
.tg .tg-dx8v{font-size:12px;vertical-align:top}
.tg .tg-yw4l{vertical-align:top}
</style>
<table class="tg">
  <tr>
    <th class="tg-dx8v">Datum:</th>
    <th class="tg-yw4l"><?php
     $datum = $_GET['date'];
     echo substr($datum, 6, 2);
     echo '.';
     echo substr($datum, 4, 2);
     echo '.';
     echo substr($datum, 0, 4);
     echo ' ';
     echo substr($datum, 8, 2);
     echo ':';
     echo substr($datum, 10, 2);
     echo ':';
     echo substr($datum, 12, 2);
     ?></th>
  </tr>
  <tr>
    <td class="tg-yw4l">Firma:</td>
    <td class="tg-yw4l"><?php echo $_GET['company']?></td>
  </tr>
  <tr>
    <td class="tg-yw4l">ICO:</td>
    <td class="tg-yw4l"><?php echo $_GET['ico']?></td>
  </tr>
  <tr>
    <td class="tg-yw4l">DIC: </td>
    <td class="tg-yw4l"><?php echo $_GET['dic']?></td>
  </tr>
  <tr>
    <td class="tg-yw4l">Provozovna:</td>
    <td class="tg-yw4l"><?php echo $_GET['address']?></td>
  </tr>
  <tr>
    <td class="tg-yw4l">Pokladna:</td>
    <td class="tg-yw4l">1</td>
  </tr>
  <tr>
    <td class="tg-yw4l">Castka:</td>
    <td class="tg-yw4l"><?php echo $_GET['value'];echo ' Kc'?></td>
  </tr>
  <tr>
    <td class="tg-yw4l">Cislo uctenky:</td>
    <td class="tg-yw4l"><?php echo $_GET['date']?></td>
  </tr>
  <tr>
    <td class="tg-yw4l">FIK:</td>
    <td class="tg-yw4l"><?php echo $_GET['fik']?></td>
  </tr>
  <tr>
    <td class="tg-yw4l">BKP:</td>
    <td class="tg-yw4l"><?php echo $_GET['bkp']?></td>
  </tr>
  <tr>
    <td class="tg-yw4l">Rezim:</td>
    <td class="tg-yw4l">Bezny</td>
  </tr>
</table>


