import React, {FunctionComponent} from "react";
import {Box} from "@mui/system";
import {
  Table, TableBody, TableCell,
  TableContainer, TableHead, TableRow,
} from "@mui/material";
import Heading from "../../../../components/Text/Heading";
import {COLOURS} from "../../../../constants/colours";

interface Props {
  data: VulnerabilityData[],
}

const VulnerabilityList: FunctionComponent<Props> = (props) => {
  if (props.data.length != 0) {
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
              {props.data.map((row, index) => (
                <TableRow
                  key={index}
                  sx={{
                    background: () => {
                      switch (row.vulnerabilityStatus) {
                        case "new":
                          return COLOURS.RED_HIGHLIGHT;
                        case "unresolved":
                          return COLOURS.YELLOW_HIGHLIGHT;
                        case "resolved":
                          return COLOURS.GREEN_HIGHLIGHT;
                      }
                    }
                  }}
                >
                  <TableCell align="left">{row.projectName}</TableCell>
                  <TableCell align="left">{row.repoName}</TableCell>
                  <TableCell align="left">{row.dependencyName}</TableCell>
                  <TableCell align="left">{row.dependencyVersion}</TableCell>
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