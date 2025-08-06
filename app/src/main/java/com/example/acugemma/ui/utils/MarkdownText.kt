package com.example.acugemma.ui.utils

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp // Import for font size
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.getTextInNode
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.parser.MarkdownParser
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.MarkdownTokenTypes

@Composable
fun MarkdownText(markdown: String, modifier: Modifier = Modifier, color: Color = Color.Unspecified) {
    Text(text = parseMarkdown(markdown), modifier = modifier, color = color)
}

fun parseMarkdown(markdown: String): AnnotatedString {
    val flavour = GFMFlavourDescriptor()
    val parser = MarkdownParser(flavour)
    val parsedTree = parser.buildMarkdownTreeFromString(markdown)

    return buildAnnotatedString {
        fun buildAnnotatedStringFromNode(node: ASTNode) {
            when (node.type) {
                MarkdownTokenTypes.ATX_HEADER -> {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        // The title content is inside the ATX_HEADER node
                        node.children.forEach {
                            // We only want the content, not the '#' tokens
                            if (it.type == MarkdownTokenTypes.ATX_CONTENT) {
                                append(it.getTextInNode(markdown).toString())
                            }
                        }
                    }
                    append("\n") // Add a newline after the title
                }
                MarkdownElementTypes.STRONG -> {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        node.children.forEach { buildAnnotatedStringFromNode(it) }
                    }
                }
                MarkdownTokenTypes.EMPH -> {
                    withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                        node.children.forEach { buildAnnotatedStringFromNode(it) }
                    }
                }
                MarkdownTokenTypes.EOL -> {
                    append("\n")
                }
                MarkdownTokenTypes.WHITE_SPACE -> {
                    append(" ")
                }
                MarkdownTokenTypes.TEXT -> {
                    append(node.getTextInNode(markdown).toString())
                }
                else -> {
                    node.children.forEach { buildAnnotatedStringFromNode(it) }
                }
            }
        }
        parsedTree.children.forEach { buildAnnotatedStringFromNode(it) }
    }
}