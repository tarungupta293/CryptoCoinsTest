package com.example.cryptocoinstest.ui.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.cryptocoinstest.R
import com.example.cryptocoinstest.data.network.NetworkState
import com.example.cryptocoinstest.domain.model.CryptoResponse
import com.example.cryptocoinstest.ui.theme.CryptoCoinsTestTheme
import com.example.cryptocoinstest.ui.utils.Util
import com.example.cryptocoinstest.ui.viewmodel.CryptoViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Tarun Gupta on 2025-11-11
 *
 * View which presents the UI on the screen.
 * Developed the complete UI with the help of Jetpack Compose.
 * Data retrieved from the ViewModel by using StateFlows.
 *
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var cryptoViewModel: CryptoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        cryptoViewModel.fetchCryptoList()

        setContent {
            CryptoCoinsTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    HomeScreen(cryptoViewModel)
                }
            }
        }
    }
}

// Main Compose handing the complete screen.
@Composable
fun HomeScreen(cryptoViewModel: CryptoViewModel) {
    val apiState by cryptoViewModel.cryptoState.collectAsState()

    Column(modifier = Modifier.padding(8.dp)) {

        Text(text = "Crypto",
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp, top = 48.dp, end = 8.dp, bottom = 8.dp))


        when(apiState) {
            is NetworkState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
            is NetworkState.Error -> {
                showEmptyUI(message = (apiState as NetworkState.Error).message)
            }
            is NetworkState.Success -> {
                val coinsList = (apiState as NetworkState.Success<List<CryptoResponse>>).data
                if (coinsList.isEmpty()) {
                    showEmptyUI("No data available. \nPlease try again in sometime.")
                } else {
                    LazyColumn {
                        items(coinsList) { coin ->
                            CoinDetail(coin)
                        }
                    }
                }
            }
        }
    }
}

// Presenting the item of coins list in the UI.
// UI showing:
// Image of Coin
// Name of the coin
//ATH: All time high value of coin
// ATH_date: date on which the coin was ATH
@Composable
fun CoinDetail(coin: CryptoResponse) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 20.dp, start = 8.dp, end = 8.dp)) {

        AsyncImage(model = coin.image,
            contentDescription = "Icon",
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            modifier = Modifier
                .clip(CircleShape)
                .weight(1f)
                .size(36.dp))

        Column(modifier = Modifier
            .weight(3f)
            .padding(start = 8.dp)) {

            Text(text = coin.name.orEmpty(),
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold)

            Text(text = "$" + coin.current_price.toString(),
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp))
        }

        Column(modifier = Modifier
            .weight(2f),
            horizontalAlignment = Alignment.End) {

            Text(text = "$" + coin.ath.toString(),
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Right
            )

            Text(text = Util.changeDateFormat(coin.ath_date.orEmpty(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp))
        }

    }
}

// UI representing the Empty screen with the error message of empty list message.
@Composable
private fun showEmptyUI(message: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .padding(4.dp, 8.dp, 4.dp, 4.dp)
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
        )
    }
}