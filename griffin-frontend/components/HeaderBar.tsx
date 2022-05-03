/**
 * By Chuyang Chen
 *
 * The headerbar component allows user to go to home, report, profile, dashboard, or setup pages
 */

import {IconButton, AppBar, Typography, Toolbar, Box} from '@mui/material';
import React, { FunctionComponent } from 'react';
 
interface Props {
 
}

const HeaderBar: FunctionComponent<Props> = (props) => {
    return (
        <React.Fragment>
            <AppBar position="sticky">
                <Toolbar>
                    <Box component="img" src="/assets/griffin-logo.png" sx={{
                        height: 35,
                        width: 35,
                        }}/>
                    <Typography variant="h4" component={"div"} style={{flexGrow: 1}}>
                        Griffin
                    </Typography>
                    <IconButton color="inherit" >
                        <Typography variant="h6">
                            Home
                        </Typography>
                    </IconButton>
                    <IconButton color="inherit" >
                        <Typography variant="h6">
                            Report
                        </Typography>
                    </IconButton>
                    <IconButton color="inherit" >
                        <Typography variant="h6">
                            Dashboard
                        </Typography>
                    </IconButton>
                    <IconButton color="inherit" >
                        <Typography variant="h6">
                            Setup
                        </Typography>
                    </IconButton>
                    <IconButton color="inherit" >
                        <Typography variant="h6">
                            Profile
                        </Typography>
                    </IconButton>
                </Toolbar>
            </AppBar>
        </React.Fragment>
    );}

export default HeaderBar;