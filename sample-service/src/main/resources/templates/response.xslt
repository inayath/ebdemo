<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" exclude-result-prefixes="#all"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:tns="http://www.example.org/test/">

	<xsl:output method="xml" version="1.0" encoding="UTF-8"
		indent="yes" />

	<xsl:template match="AggregationResult">
		<tns:response>
			<xsl:for-each select="tns:response">
				<tns:status>
					<xsl:value-of select="tns:status" />
				</tns:status>
			</xsl:for-each>
		</tns:response>
	</xsl:template>


	<!-- Ignore Any extra info -->
	<xsl:template match="text()|@*">
	</xsl:template>

</xsl:stylesheet>
