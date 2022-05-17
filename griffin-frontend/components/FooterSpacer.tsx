import React, {FunctionComponent} from 'react';
import {Box} from "@mui/material";

interface Props {

}

const FooterSpacer: FunctionComponent<Props> = (props) => {

  return (
    <Box
      sx={{height: "2rem"}}
    />
  );
};

export default FooterSpacer;