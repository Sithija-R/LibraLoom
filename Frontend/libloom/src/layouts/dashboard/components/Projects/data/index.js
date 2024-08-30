/* eslint-disable react/prop-types */
/* eslint-disable react/function-component-definition */
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
import Tooltip from "@mui/material/Tooltip";
import MDBox from "components/MDBox";
import MDTypography from "components/MDTypography";
import MDAvatar from "components/MDAvatar";
import MDProgress from "components/MDProgress";

// Images

import Book1 from "assets/images/Book1.jpg";
import Book2 from "assets/images/Book2.jpg";
import Book3 from "assets/images/Book3.jpg";
import Book4 from "assets/images/Book4.jpg";

export default function data() {
  const avatars = (members) =>
    members.map(([image, name]) => (
      <Tooltip key={name} title={name} placeholder="bottom">
        <MDAvatar
          src={image}
          alt="name"
          size="xs"
          sx={{
            border: ({ borders: { borderWidth }, palette: { white } }) =>
              `${borderWidth[2]} solid ${white.main}`,
            cursor: "pointer",
            position: "relative",

            "&:not(:first-of-type)": {
              ml: -1.25,
            },

            "&:hover, &:focus": {
              zIndex: "10",
            },
          }}
        />
      </Tooltip>
    ));

  const Company = ({ image, name }) => (
    <MDBox display="flex" alignItems="center" lineHeight={1}>
      <img src={image} name={name} style={{ height: "60px" }} />
      <MDTypography variant="button" fontWeight="medium" ml={1} lineHeight={1}>
        {name}
      </MDTypography>
    </MDBox>
  );

  return {
    columns: [
      { Header: "Books", accessor: "companies", width: "45%", align: "left" },
      { Header: "Borrower", accessor: "members", width: "10%", align: "left" },
      { Header: "Days", accessor: "budget", align: "center" },
      { Header: "Overdue", accessor: "completion", align: "center" },
    ],

    rows: [
      {
        companies: <Company image={Book1} name="Material UI XD Version" />,
        members: (
          <MDTypography variant="caption" color="text" fontWeight="medium">
            W D Sithija Vinod
          </MDTypography>
        ),
        budget: (
          <MDTypography variant="caption" color="text" fontWeight="medium">
            3 Days
          </MDTypography>
        ),
        completion: (
          <MDTypography variant="caption" color="text" fontWeight="medium">
            28/08/2024
          </MDTypography>
        ),
      },
      {
        companies: <Company image={Book2} name="Add Progress Track" />,
        members: (
          <MDTypography variant="caption" color="text" fontWeight="medium">
            W D Sithija Vinod
          </MDTypography>
        ),
        budget: (
          <MDTypography variant="caption" color="text" fontWeight="medium">
            5 Days
          </MDTypography>
        ),
        completion: (
          <MDTypography variant="caption" color="text" fontWeight="medium">
            23/08/2024
          </MDTypography>
        ),
      },
      {
        companies: <Company image={Book3} name="Fix Platform Errors" />,
        members: (
          <MDTypography variant="caption" color="text" fontWeight="medium">
            W D Sithija Vinod
          </MDTypography>
        ),
        budget: (
          <MDTypography variant="caption" color="text" fontWeight="medium">
            1 week
          </MDTypography>
        ),
        completion: (
          <MDTypography variant="caption" color="text" fontWeight="medium">
            20/08/2023
          </MDTypography>
        ),
      },
      {
        companies: <Company image={Book4} name="Launch our Mobile App" />,
        members: (
          <MDTypography variant="caption" color="text" fontWeight="medium">
            W D Sithija Vinod
          </MDTypography>
        ),
        budget: (
          <MDTypography variant="caption" color="text" fontWeight="medium">
            1 Day
          </MDTypography>
        ),
        completion: (
          <MDTypography variant="caption" color="text" fontWeight="medium">
            29/08/2024
          </MDTypography>
        ),
      },
      {
        companies: <Company image={Book4} name="Add the New Pricing Page" />,
        members: (
          <MDTypography variant="caption" color="text" fontWeight="medium">
            W D Sithija Vinod
          </MDTypography>
        ),
        budget: (
          <MDTypography variant="caption" color="text" fontWeight="medium">
            1 week
          </MDTypography>
        ),
        completion: (
          <MDTypography variant="caption" color="text" fontWeight="medium">
            20/08/2024
          </MDTypography>
        ),
      },
      {
        companies: <Company image={Book1} name="Redesign New Online Shop" />,
        members: (
          <MDTypography variant="caption" color="text" fontWeight="medium">
            W D Sithija Vinod
          </MDTypography>
        ),
        budget: (
          <MDTypography variant="caption" color="text" fontWeight="medium">
            5 Days
          </MDTypography>
        ),
        completion: (
          <MDTypography variant="caption" color="text" fontWeight="medium">
            20/08/2024
          </MDTypography>
        ),
      },
    ],
  };
}
