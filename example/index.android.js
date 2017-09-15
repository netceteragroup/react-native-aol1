import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  Switch,
  Dimensions,
  ScrollView,
  TextInput
} from 'react-native';

import { AdTechView } from 'react-native-adtech';

export default class example extends Component {
  render() {
    const kValues = {
      kvenv: 'test',
      kvarticle: ['none']
    }

    return (

      <View style={styles.container}>
        <Text style={styles.text}>
           Text Above Banner Ad
        </Text>
        <AdTechView
          style={styles.empty_syle}
          alias={'home-top-5'}
          networkId={23}
          subnetworkId={4}
          type={'banner'}
          keyValues={kValues}
          height={50}
          onAdFetchSuccess={() => console.log('New ad fetched')}
          />
        <Text style={styles.text}>
           Text Below Banner Ad
        </Text>
         <AdTechView
          style={styles.empty_syle}
          alias={'interstitial-top-5'}
          networkId={23}
          subnetworkId={4}
          type={'interstitial'}
          //height={0}
          />
          <TextInput style={{width:250}}/>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  text: {
    textAlign: 'center',
    color: '#000000',
  },

});

AppRegistry.registerComponent('example', () => example);
