import React, {FunctionComponent} from 'react';
import PageContainer from "./PageContainer";
import {Box, CircularProgress} from "@mui/material";
import Heading from "./Text/Heading";

interface Props {

}

const ErrorPage: FunctionComponent<Props> = (props) => {

  return (
    <PageContainer centered>
      <CircularProgress/>
      <Box sx={{width: "1rem"}}/>
      <Heading>
        Error
      </Heading>
    </PageContainer>
  );
};

export default ErrorPage;