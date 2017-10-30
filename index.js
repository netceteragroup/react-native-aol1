'use strict'

import React, { Component } from 'react'
import { requireNativeComponent } from 'react-native'
import PropTypes from 'prop-types'

class AdTechView extends Component {
  static propTypes = {
    alias: PropTypes.string.isRequired,
    type: PropTypes.string.isRequired,
    networkId: PropTypes.number.isRequired,
    subnetworkId: PropTypes.number.isRequired,
    autoload: PropTypes.bool,
    keyValues: PropTypes.object,
    onAdFetchSuccess: PropTypes.func,
    onAdFetchFail: PropTypes.func,
    onInterstitialHidden: PropTypes.func,
  }

  render() {
    return <RCTAdTechView {...this.props} />
  }
}

AdTechView.defaultProps = {
  autoload: true,
}

const RCTAdTechView = requireNativeComponent('AdtechView', null)

export { AdTechView }
