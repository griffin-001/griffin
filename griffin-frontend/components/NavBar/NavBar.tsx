/**
 * By Chuyang Chen
 *
 * The NavBar component allows user to go to home, report, profile, dashboard, or setup pages
 */

import {IconButton, AppBar, Typography, Toolbar, Box} from '@mui/material';
import React, {FunctionComponent} from 'react';
import NavBarBtn from "./NavBarBtn";
import {COLOURS} from "../../constants/colours";

interface Props {

}

const NavBar: FunctionComponent<Props> = (props) => {
  return (
    <Box sx={{
      position: "absolute",
      top: 0,
      left: 0,
      display: "flex",
      justifyContent: "center",
      alignItems: "center",
      height: "64px",
      width: "100%",
      paddingLeft: "1rem",
      borderBottom: "solid 1px " + COLOURS.BLACK_TEXT,
    }}>
      <Box sx={{
        display: "flex",
        flexDirection: "row",
        justifyContent: "center",
        alignItems: "center",
        width: "100%"
      }}>
        <Box component="img" src="/assets/griffin-logo.png" sx={{
          height: 50,
          width: 50,
          marginRight: "0.5rem",
        }}/>
        <Typography variant="h4" component={"div"} style={{flexGrow: 1}}
                    fontFamily={"Courier"} fontWeight={"bold"}>
          Griffin
        </Typography>
      </Box>
      <Box sx={{
        display: "flex",
        flexDirection: "row",
        paddingRight: "1rem",
        marginRight: "calc(1rem + 100% - 100vw)",
      }}>
        <NavBarBtn text={"Home"} link={"/"}/>
        <NavBarBtn text={"Report"} link={"/vulnerabilitiesReport"}/>
        <NavBarBtn text={"Dashboard"} link={"/projectsDashboard"}/>
        <NavBarBtn text={"Setup"} link={"/initiateScan"}/>
        <NavBarBtn text={"Profile"} link={"/profile"}/>
      </Box>
    </Box>
  )
}

export default NavBar;