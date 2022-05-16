import React, {FunctionComponent} from "react";
import {Box} from "@mui/system";
import {
  Stack,
  Table, TableBody, TableCell,
  TableContainer, TableHead, TableRow,
  Typography
} from "@mui/material";
import Heading from "../../../../components/Text/Heading";
import BodyText from "../../../../components/Text/BodyText";
import {COLOURS} from "../../../../constants/colours";
import ResultsTableFooter from "./ResultsTableFooter";

interface Props {
  items: Array<Vulnerability>,
}

const VulnerabilityList: FunctionComponent<Props> = (props) => {
  if (props.items.length != 0) {
    return (
      <Box>
        <TableContainer sx={{maxHeight: "50vh", px: 2, pb: 2}}>
          <Table stickyHeader aria-label="sticky table">
            <TableHead>
              <TableRow>
                {/* todo this is the location */}
                <TableCell> Project </TableCell>
                <TableCell> Repository </TableCell>
                {/* todo this is the dependency impacted */}
                <TableCell> Dependency </TableCell>
                <TableCell> Version </TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {props.items.map((vul) => (
                <TableRow
                  key={vul.name}
                  sx={{background: COLOURS.GREEN_HIGHLIGHT}}
                >
                  <TableCell align="left">{"project name"}</TableCell>
                  <TableCell align="left">{"repo name"}</TableCell>
                  <TableCell align="left">{vul.dependency.name}</TableCell>
                  <TableCell align="left">{vul.dependency.version}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>

          {/* todo not needed for now, please keep for later sprints*/}
          {/*<ResultsTableFooter/>*/}

        </TableContainer>

      </Box>
    )
  } else {
    return (
      <Box sx={{mt: 2, mx: 5}}>
        <Heading>No Vulnerability!</Heading>
      </Box>
    )
  }
}

export default VulnerabilityList;