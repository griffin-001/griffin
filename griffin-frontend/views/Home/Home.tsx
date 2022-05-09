import React, {FunctionComponent} from 'react';
import NavBar from "../../components/NavBar/NavBar";
import PageContainer from "../../components/PageContainer";
import {Box, Typography, Grid, Button} from "@mui/material";

interface Props {

}

// todo NOTE: All local components / hooks / utils etc.
//  can just go within the landing folder in views
//  Only the global stuff needs to go in a separate folder

class Home extends React.Component<Props> {
  render() {

    return (
      <PageContainer isCentered>
        <Typography style={{fontWeight: 600}} variant={"h5"}>
          Alerts
        </Typography>
      </PageContainer>
    );
  }
}

export default Home;