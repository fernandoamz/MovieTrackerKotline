package com.example.movietracker.presentation.shows

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.movietracker.domain.model.Show
import com.example.movietracker.ui.theme.MovieTrackerTheme

/**
 * Sobrecarga de conveniencia que acepta el modelo de dominio [Show].
 */
@Composable
fun MediaListItem(
    show: Show,
    modifier: Modifier = Modifier,
    onFavoriteClick: (() -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    MediaListItem(
        title = show.name ?: "Sin Título",
        language = show.language,
        genres = show.genres,
        imageUrl = show.imageUrl,
        isFavorite = show.isFavorite,
        onFavoriteClick = onFavoriteClick,
        modifier = modifier,
        onClick = onClick
    )
}

/**
 * Componente modular y reutilizable para representar un elemento de lista de películas o series.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MediaListItem(
    title: String,
    language: String?,
    genres: List<String>,
    imageUrl: String?,
    isFavorite: Boolean = false,
    onFavoriteClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Póster / Imagen con soporte para Placeholder y Error vía Coil SubcomposeAsyncImage
            SubcomposeAsyncImage(
                model = imageUrl,
                contentDescription = title,
                modifier = Modifier
                    .width(100.dp)
                    .height(145.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerHighest),
                contentScale = ContentScale.Crop,
                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                error = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceContainerHighest),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "🎬",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Sin Imagen",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            )

            // Columna de Información y Detalles
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(145.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        // Título Principal
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )

                        // Botón de Favorito
                        if (onFavoriteClick != null) {
                            IconButton(
                                onClick = onFavoriteClick,
                                modifier = Modifier.size(32.dp)
                            ) {
                                Text(
                                    text = if (isFavorite) "❤️" else "🤍",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }

                    // Badge de Idioma
                    language?.takeIf { it.isNotBlank() }?.let { lang ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "🌐",
                                    style = MaterialTheme.typography.labelSmall
                                )
                                Text(
                                    text = lang.uppercase(),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Chips / Badges de Géneros
                if (genres.isNotEmpty()) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        maxItemsInEachRow = 3
                    ) {
                        genres.take(3).forEach { genre ->
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.8f),
                                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                            ) {
                                Text(
                                    text = genre,
                                    style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MediaListItemPreview() {
    MovieTrackerTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            MediaListItem(
                title = "Breaking Bad",
                language = "English",
                genres = listOf("Drama", "Crime", "Thriller"),
                imageUrl = "https://static.tvmaze.com/uploads/images/medium_portrait/0/2400.jpg",
                isFavorite = true,
                onFavoriteClick = {}
            )
        }
    }
}
