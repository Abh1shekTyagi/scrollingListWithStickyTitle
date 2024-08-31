package com.example.basiccodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import com.example.basiccodelab.ui.theme.BasicCodelabTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicCodelabTheme {
                var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

                if (shouldShowOnboarding) {
                    OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
                } else
                    MyContent()
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun MyContent(
        modifier: Modifier = Modifier,
        name: HashMap<String, List<String>> = hashMapOf(
            "India" to listOf(
                "Mumbai", "Delhi", "Bangalore", "Hyderabad", "Chennai",
                "Kolkata", "Ahmedabad", "Pune", "Jaipur", "Lucknow"
            ),
            "USA" to listOf(
                "New York City", "Los Angeles", "Chicago", "Houston", "Phoenix",
                "Philadelphia", "San Antonio", "San Diego", "Dallas", "San Jose"
            ),
            "Germany" to listOf(
                "Berlin", "Munich", "Hamburg", "Cologne", "Frankfurt",
                "Stuttgart", "Düsseldorf", "Dortmund", "Essen", "Leipzig"
            ),
            "France" to listOf(
                "Paris", "Marseille", "Lyon", "Toulouse", "Nice",
                "Nantes", "Strasbourg", "Montpellier", "Bordeaux", "Lille"
            ),
            "Switzerland" to listOf(
                "Zürich", "Geneva", "Basel", "Lausanne", "Bern",
                "Winterthur", "Lucerne", "St. Gallen", "Lugano", "Biel/Bienne"
            )
        )

    ) {
        Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
            LazyColumn(modifier = modifier.padding(innerPadding)) {
                name.forEach { (t, u) ->
                    stickyHeader {
                        Surface(modifier = modifier.fillMaxWidth(1f)
                        ) {
                            Text(
                                modifier = modifier.padding(start = 20.dp, top = 20.dp, bottom = 8.dp),
                                text = t,
                                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold)
                            )
                        }
                    }
                    items(u) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                        ) {
                            Greeting(name = it, modifier = modifier)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OnboardingScreen(modifier: Modifier = Modifier, onContinueClicked: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val expandedPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp, label = "",
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(modifier = modifier.padding(24.dp)) {
            Column(
                modifier = modifier
                    .weight(1f)
                    .padding(bottom = expandedPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Hello,")
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold)
                )
                if (expanded) {
                    Text(
                        text = ("Composem ipsum color sit lazy, " +
                                "padding theme elit, sed do bouncy. ").repeat(4),
                    )
                }
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (!expanded) Icons.Filled.ExpandMore else Icons.Filled.ExpandLess,
                    contentDescription = if (expanded) stringResource(R.string.show_less) else stringResource(
                        R.string.show_more
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BasicCodelabTheme {
        Greeting("Android")
    }
}