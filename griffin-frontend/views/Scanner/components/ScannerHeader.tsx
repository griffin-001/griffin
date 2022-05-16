import React, {FunctionComponent, useState} from 'react';
import {Button, Grid} from "@mui/material";
import Heading from "../../../components/Text/Heading";
import {Box} from "@mui/system";
import Section from "../../../components/Section";

interface Props {
  runScanner: () => void;
}

const ScannerHeader: FunctionComponent<Props> = (props) => {


  const [value, setValue] = useState(false);

  const handleButton = () => {
    value ? setValue(false) : setValue(true);
  }

  return (
    <Section>
      <Grid
        container
        spacing={2}
        sx={{
          height: "100%",
          display: "flex",
          alignItems: "center",
        }}
      >
        <Grid
          item xs={8}
          sx={{
            height: "100%",
            display: "flex",
            alignItems: "center",
          }}
        >
          <Heading> Scan History </Heading>
        </Grid>
        <Grid item xs={4}>

          <Box display="flex" justifyContent="flex-end">
            <Button
              variant="outlined"
              size="large"
              onClick={(event) =>
                props.runScanner()}
            >
              Start New Scan
            </Button>
          </Box>
        </Grid>
      </Grid>
    </Section>
  );
};

export default ScannerHeader;