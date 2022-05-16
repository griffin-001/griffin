import React, {FunctionComponent} from 'react';
import PageContainer from "./PageContainer";
import {Box, CircularProgress} from "@mui/material";
import Heading from "./Text/Heading";

interface Props {

}

const LoadingPage: FunctionComponent<Props> = (props) => {

  return (
    <PageContainer centered>
      <CircularProgress/>
      <Box sx={{width: "1rem"}}/>
      <Heading>
        Loading
      </Heading>
    </PageContainer>
  );
};

export default LoadingPage;