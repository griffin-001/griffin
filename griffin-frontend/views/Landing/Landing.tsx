import React, { FunctionComponent } from 'react';
import HeaderBar from '../../components/HeaderBar';
import {Box, Typography} from "@mui/material";
import LandingTopPanel from "./LandingTopPanel";

interface Props {

}

// todo NOTE: All local components / hooks / utils etc.
//  can just go within the landing folder in views
//  Only the global stuff needs to go in a separate folder

const Landing: FunctionComponent<Props> = (props) => {

  return (
    <React.Fragment>
      <HeaderBar/>
      <div style={{display: 'flex',  justifyContent:'center', alignItems:'center', left: '50%'}}>
        <LandingTopPanel/>
      </div>
    </React.Fragment>
  );
};

export default Landing;