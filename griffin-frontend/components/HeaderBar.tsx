/**
 * By Chuyang Chen
 *
 * The headerbar component allows user to go to home, report, profile, dashboard, or setup pages
 */

import * as React from 'react';
import {AppBar, Icon, IconButton, Toolbar, Typography} from "@material-ui/core";
import RouteURLs from "../utils/RouteURLs";

type Props = {

};

type State = {

};



export class HeaderBar extends React.Component<Props, State> {
    render() {
        return (
            <div>
                <AppBar>
                    <Toolbar>
                        <Typography variant="h6" component={"div"} style={{flexGrow: 1}}>
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
            </div>
        );
    };
}