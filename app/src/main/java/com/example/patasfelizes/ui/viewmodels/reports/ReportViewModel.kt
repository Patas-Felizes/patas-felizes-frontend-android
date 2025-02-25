package com.example.patasfelizes.ui.viewmodels.reports

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Procedure
import com.example.patasfelizes.repository.ProcedureRepository
import com.example.patasfelizes.repository.AnimalsRepository
import com.example.patasfelizes.repository.DonationRepository
import com.example.patasfelizes.repository.ExtenseRepository
import com.example.patasfelizes.repository.CampaignsRepository
import com.example.patasfelizes.repository.AdoptionsRepository
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.UnitValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.io.FileOutputStream
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.*
import android.util.Log

class ReportViewModel : ViewModel() {
    // Repositories
    private val procedureRepository = ProcedureRepository()
    private val animalsRepository = AnimalsRepository()
    private val donationRepository = DonationRepository()
    private val extenseRepository = ExtenseRepository()
    private val campaignsRepository = CampaignsRepository()
    private val adoptionsRepository = AdoptionsRepository()

    // Scope para operações assíncronas
    private val viewModelScope = CoroutineScope(Dispatchers.Main + Job())

    // State para gerenciar o estado de geração
    private val _isGenerating = MutableStateFlow(false)
    val isGenerating: StateFlow<Boolean> = _isGenerating.asStateFlow()

    // State para mensagens de status
    private val _generationMessage = MutableStateFlow("")
    val generationMessage: StateFlow<String> = _generationMessage.asStateFlow()

    // State para tipo de mensagem (sucesso/erro)
    private val _isSuccess = MutableStateFlow(true)
    val isSuccess: StateFlow<Boolean> = _isSuccess.asStateFlow()

    // States para as datas do período do relatório
    private val _startDate = MutableStateFlow(getCurrentDateFormatted())
    val startDate: StateFlow<String> = _startDate.asStateFlow()

    private val _endDate = MutableStateFlow(getCurrentDateFormatted())
    val endDate: StateFlow<String> = _endDate.asStateFlow()

    // Função para definir a data inicial
    fun setStartDate(date: String) {
        _startDate.value = date
    }

    // Função para definir a data final
    fun setEndDate(date: String) {
        _endDate.value = date
    }

    // Obtém a data atual formatada
    private fun getCurrentDateFormatted(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }

    // Função principal para gerar o relatório
    fun generateReport(context: Context, reportType: String) {
        _isGenerating.value = true
        _generationMessage.value = "Gerando relatório de $reportType..."

        viewModelScope.launch {
            try {
                when (reportType) {
                    "Procedimentos" -> generateProceduresReport(context)
                    "Animais" -> { /* Implementar no futuro */ }
                    "Doações" -> { /* Implementar no futuro */ }
                    "Despesas" -> { /* Implementar no futuro */ }
                    "Campanhas" -> { /* Implementar no futuro */ }
                    "Adoções" -> { /* Implementar no futuro */ }
                }
            } catch (e: Exception) {
                Log.e("ReportViewModel", "Erro ao gerar relatório", e)
                _isSuccess.value = false
                _generationMessage.value = "Erro ao gerar relatório: ${e.message}"
            } finally {
                _isGenerating.value = false
            }
        }
    }

    // Função para gerar relatório de procedimentos
    private suspend fun generateProceduresReport(context: Context) {
        val procedures = fetchProceduresData()
        if (procedures.isEmpty()) {
            _isSuccess.value = false
            _generationMessage.value = "Não há procedimentos para gerar o relatório."
            return
        }

        try {
            val pdfFile = createProceduresPdfFile(context, procedures)
            openPdfFile(context, pdfFile)

            _isSuccess.value = true
            _generationMessage.value = "Relatório de procedimentos gerado com sucesso!"
        } catch (e: Exception) {
            throw e
        }
    }

    // Busca os dados de procedimentos
    private suspend fun fetchProceduresData(): List<Procedure> = suspendCancellableCoroutine { continuation ->
        procedureRepository.listProcedures(
            onSuccess = { procedures ->
                // Filtrar por período, se necessário
                val filteredProcedures = filterProceduresByDateRange(procedures)
                continuation.resume(filteredProcedures) {}
            },
            onError = { error ->
                Log.e("ReportViewModel", "Erro ao buscar procedimentos: $error")
                continuation.resume(emptyList()) {}
            }
        )
    }

    // Filtra procedimentos por período de datas
    private fun filterProceduresByDateRange(procedures: List<Procedure>): List<Procedure> {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val startDateParsed = try {
            dateFormat.parse(_startDate.value)
        } catch (e: Exception) {
            dateFormat.parse(getCurrentDateFormatted())
        }

        val endDateParsed = try {
            dateFormat.parse(_endDate.value)
        } catch (e: Exception) {
            dateFormat.parse(getCurrentDateFormatted())
        }

        return procedures.filter {
            try {
                val procedureDate = dateFormat.parse(it.data_procedimento)
                procedureDate != null &&
                        !procedureDate.before(startDateParsed) &&
                        !procedureDate.after(endDateParsed)
            } catch (e: Exception) {
                false
            }
        }
    }

    // Cria o arquivo PDF para procedimentos
    private fun createProceduresPdfFile(context: Context, procedures: List<Procedure>): File {
        val fileName = "procedimentos_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())}.pdf"
        val file = File(context.cacheDir, fileName)

        PdfWriter(FileOutputStream(file)).use { writer ->
            val pdfDoc = PdfDocument(writer)
            val document = Document(pdfDoc)

            // Título do relatório
            val title = Paragraph("Relatório de Procedimentos")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(20f)
                .setBold()
            document.add(title)

            // Período do relatório
            val period = Paragraph("Período: ${_startDate.value} a ${_endDate.value}")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(12f)
            document.add(period)

            // Data de geração do relatório
            val generationDate = Paragraph("Gerado em: ${getCurrentDateFormatted()} às ${SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())}")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(10f)
                .setItalic()
            document.add(generationDate)

            document.add(Paragraph("\n"))

            // Criar tabela de procedimentos
            val table = Table(UnitValue.createPercentArray(floatArrayOf(15f, 30f, 15f, 15f, 25f)))
                .setWidth(UnitValue.createPercentValue(100f))

            // Cabeçalho da tabela
            addHeaderCell(table, "Tipo")
            addHeaderCell(table, "Descrição")
            addHeaderCell(table, "Valor (R$)")
            addHeaderCell(table, "Data")
            addHeaderCell(table, "Observações")

            // Adicionar dados dos procedimentos
            val currencyFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
            procedures.forEach { procedure ->
                addCell(table, procedure.tipo)
                addCell(table, procedure.descricao)

                // Formatação do valor para moeda
                val valor = try {
                    procedure.valor.toDouble()
                } catch (e: Exception) {
                    0.0
                }
                addCell(table, currencyFormat.format(valor).replace("R$", "").trim())

                addCell(table, procedure.data_procedimento)
                addCell(table, "ID: ${procedure.procedimento_id}")
            }

            document.add(table)

            // Resumo financeiro
            document.add(Paragraph("\n"))
            val totalValue = procedures.sumOf {
                try {
                    it.valor.toDouble()
                } catch (e: Exception) {
                    0.0
                }
            }

            val summary = Paragraph("Total de Procedimentos: ${procedures.size} | Valor Total: ${currencyFormat.format(totalValue)}")
                .setTextAlignment(TextAlignment.RIGHT)
                .setFontSize(12f)
                .setBold()
            document.add(summary)

            document.close()
        }

        return file
    }

    // Adiciona célula de cabeçalho à tabela
    private fun addHeaderCell(table: Table, text: String) {
        table.addHeaderCell(
            Cell().add(Paragraph(text))
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
        )
    }

    // Adiciona célula normal à tabela
    private fun addCell(table: Table, text: String) {
        table.addCell(
            Cell().add(Paragraph(text))
                .setTextAlignment(TextAlignment.LEFT)
        )
    }

    // Abre o arquivo PDF após a geração
    private fun openPdfFile(context: Context, file: File) {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            flags = Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
        }

        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            _isSuccess.value = false
            _generationMessage.value = "Nenhum aplicativo encontrado para abrir o PDF. O arquivo foi salvo em ${file.absolutePath}"
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}