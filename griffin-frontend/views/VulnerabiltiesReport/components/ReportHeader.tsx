import React, {FunctionComponent, useState} from 'react';
import {Button, Grid} from "@mui/material";
import Heading from "../../../components/Text/Heading";
import {Box} from "@mui/system";
import Section from "../../../components/Section";

interface Props {

}

const ReportHeader: FunctionComponent<Props> = (props) => {


  const [value, setValue] = useState(false);

  const handleButton = () => {
    value ? setValue(false) : setValue(true);
  }

  return (
    <Section>
      <Grid container spacing={2}>
        <Grid item xs={8}>
          <Heading> Vulnerability Report </Heading>
        </Grid>
        <Grid item xs={4}>

          <Box display="flex" justifyContent="flex-end">
            <Button
              variant="outlined"
              size="large"
              onClick={(event) =>
                handleButton()}
            >
              Scan
            </Button>
          </Box>
        </Grid>
      </Grid>
    </Section>
  );
};

export default ReportHeader;