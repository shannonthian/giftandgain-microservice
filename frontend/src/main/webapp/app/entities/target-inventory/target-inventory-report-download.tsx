import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Button, Row, Col, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { addMonth, getMonthYearString } from 'app/shared/util/date-utils';
import { IReport, ReportState, downloadReport, getReports } from './target-inventory-report-download.reducer';
import { AxiosResponse } from 'axios';

export const TargetInventoryReportDownload = () => {
  const dispatch = useAppDispatch();

  const { loading, reports }: ReportState = useAppSelector(state => state.reportDownload);

  useEffect(() => {
    dispatch(getReports());
  }, []);

  const monthOptions = (noOfMonths: number) => {
    const options: IReport[] = [];
    for (let i = 0; i < noOfMonths; i++) {
      options.push(
        {
          month: +addMonth(1, -i, "M"),
          year: +addMonth(1, -i, "YYYY")
        }
      );
    }
    return options;
  };

  const decodeBase64Str = (base64Str: string) => {
    return Buffer.from(base64Str, 'base64').toString();
  };

  const downloadData = (report: IReport) => {
    const data = decodeBase64Str(report.data);
    const filename = `giftandgain-performance-report-${report.year}-${report.month < 10 ? '0' : ''}${report.month}.csv`;
    const blob = new Blob([data], { type: 'text/csv' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.setAttribute('href', url);
    a.setAttribute('download', filename);
    a.click();
  };

  const onClickDownload = (report: IReport) => {
    const month = report.month;
    const year = report.year;
    report = reports.filter((item) => item.month === month && item.year === year)[0];
    console.log(report);
    if (report) {
      downloadData(report);
    } else {
      dispatch(
        downloadReport({
          month,
          year,
        })
      ).then((response) => {
        if (response.meta.requestStatus === 'fulfilled') {
          const payload = response.payload as AxiosResponse;
          const data = payload.data.payload[0];
          downloadData({
            month,
            year,
            data,
          });
        }
      });
    }
  };

  return (
    <Row className="justify-content-center">
      <Col md="6">
        <h2>
          Performance Reports
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th />
                <th />
              </tr>
            </thead>
            <tbody>
              {monthOptions(6).map((report, i) => (
                <tr key={i}>
                  <td>{getMonthYearString(report.month, report.year)}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button color="secondary" onClick={() => onClickDownload(report)} disabled={loading}>
                        <FontAwesomeIcon icon="download" />
                        &nbsp;
                        <span className="d-none d-md-inline">
                          Download
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
        <Button tag={Link} to="/target-inventory" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  )
};

export default TargetInventoryReportDownload;
