/**
=========================================================
* Material Dashboard 2 React - v2.2.0
=========================================================

* Product Page: https://www.creative-tim.com/product/material-dashboard-react
* Copyright 2023 Creative Tim (https://www.creative-tim.com)

Coded by www.creative-tim.com

 =========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
*/

// @mui material components
import Card from "@mui/material/Card";
// import Divider from "@mui/material/Divider";
import Icon from "@mui/material/Icon";
import TextField from "@mui/material/TextField";
// Material Dashboard 2 React components
import MDBox from "components/MDBox";
import MDTypography from "components/MDTypography";
// import MDButton from "components/MDButton";
import Button from "@mui/material/Button";
// Billing page components
import Transaction from "layouts/billing/components/Transaction";

function Transactions() {
  return (
    <Card sx={{ height: "100%" }}>
      <MDBox display="flex" justifyContent="space-between" alignItems="center" pt={3} px={2}>
        <MDTypography variant="h6" fontWeight="medium" textTransform="capitalize">
          Add New Books&apos;
        </MDTypography>
      </MDBox>
      <MDBox pt={3} pb={2} px={2}>
        <MDBox
          component="ul"
          display="flex"
          flexDirection="column"
          p={0}
          m={0}
          sx={{ listStyle: "none" }}
        >
          <TextField id="standard-basic" label="Book Name" variant="standard" />
          <TextField id="standard-basic" label="Author" variant="standard" />
          <TextField id="standard-basic" label="Prize" variant="standard" />
          <TextField id="standard-basic" label="Publisher" variant="standard" />
        </MDBox>
        <Button variant="contained" style={{ margin: "20px", color: "white" }}>
          Save Info
        </Button>
      </MDBox>

      <MDBox display="flex" justifyContent="space-between" alignItems="center" pt={3} px={2}>
        <MDTypography variant="h6" fontWeight="medium" textTransform="capitalize">
          Add New Members&apos;
        </MDTypography>
      </MDBox>
      <MDBox pt={3} pb={2} px={2}>
        <MDBox
          component="ul"
          display="flex"
          flexDirection="column"
          p={0}
          m={0}
          sx={{ listStyle: "none" }}
        >
          <TextField id="standard-basic" label="Name" variant="standard" />
          <TextField id="standard-basic" label="Registration No" variant="standard" />
          <TextField id="standard-basic" label="Date of Birth" variant="standard" />
          <TextField id="standard-basic" label="Email" variant="standard" />
          <TextField id="standard-basic" label="Contact No" variant="standard" />
        </MDBox>
        <Button variant="contained" style={{ margin: "20px", color: "white" }}>
          Save Info
        </Button>
      </MDBox>
    </Card>
  );
}

export default Transactions;
