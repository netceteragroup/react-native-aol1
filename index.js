'use strict';

import React, { Component } from "react";
import { requireNativeComponent } from 'react-native';
import PropTypes, { string, func, number, object } from 'prop-types'

class AdTechView extends Component {
  static propTypes = {
    alias: string.isRequired,
    type: string.isRequired,
    networkId: number.isRequired,
    subnetworkId: number.isRequired,
    keyValues: object,
    onAdFetchSuccess: func,
    onAdFetchFail: func,
    onInterstitialHidden: func
  }

  render() {
    return <RCTAdTechView {...this.props}/>
  }
}

const RCTAdTechView = requireNativeComponent('AdtechView', null);

export {
  AdTechView
};
